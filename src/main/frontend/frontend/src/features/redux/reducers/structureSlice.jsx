import {createAsyncThunk, createEntityAdapter, createSlice} from "@reduxjs/toolkit";
import axios from "../../utils/axiosCustom";

export const locationsAdapter = createEntityAdapter();
export const locationsSelectors = locationsAdapter.getSelectors((state) => state.locations);
export const buildingsAdapter = createEntityAdapter();
export const buildingsSelectors = buildingsAdapter.getSelectors((state) => state.locations.buildings);
export const devicesAdapter = createEntityAdapter();
export const devicesSelectors = devicesAdapter.getSelectors((state) => state.locations.devices);

export const fetchStructureAsync = createAsyncThunk("structure/fetchStructureAsync",
  async () => {
    const data = (await axios.get("api/v1/location")).data;

    const mappedData = data.map((location) => ({
      ...location,
      buildings: location.buildings.map((building) => ({
        ...building,
        locationId: location.id,
        devices: building.devices.map((device) => ({
          ...device,
          buildingId: building.id,
          locationId: location.id,
        }))
      }))
    }));

    const locations = mappedData.map(({buildings, ...locations}) => ({
      ...locations,
      buildings: buildings.map(({devices, id, locationId}) => ({
        id,
        locationId,
        devices: devices.map(({id, buildingId}) => ({
          id,
          buildingId,
        })),
      })),
    }));
    const buildings = mappedData.reduce((prev, curr) => [...prev, curr.buildings], []).flat();
    const devices = buildings.reduce((prev, curr) => [...prev, curr.devices], []).flat();

    return {locations, buildings, devices}
  },
);

export const deleteLocationAsync = createAsyncThunk("structure/deleteLocationAsync",
  async (id) => {
    await axios.delete("api/v1/location/delete/" + id);
    return id;
  },
);

export const deleteBuildingAsync = createAsyncThunk("structure/deleteBuildingAsync",
  async (id) => {
    await axios.delete("api/v1/building/delete/" + id);
    return id;
  },
);

export const deleteDeviceAsync = createAsyncThunk("structure/deleteDeviceAsync",
  async (id) => {
    await axios.delete("api/v1/device/delete/" + id);
    return id;
  },
);

const structureSlice = createSlice({
  name: "structure",
  initialState: locationsAdapter.getInitialState({
    buildings: buildingsAdapter.getInitialState(),
    devices: devicesAdapter.getInitialState(),
    status: null,
    error: null,
  }),
  reducers: {
    setAllBuildings: buildingsAdapter.setAll,
    removeAllLocations: locationsAdapter.removeAll,
    removeAllBuildings: buildingsAdapter.removeAll,
    removeAllDevices: devicesAdapter.removeAll,
  },
  extraReducers: {
    [fetchStructureAsync.pending]: (state) => {
      state.status = "loading";
      state.error = null;
    },
    [fetchStructureAsync.fulfilled]: (state, {payload}) => {
      state.status = "resolved";
      state.error = null;
      locationsAdapter.setAll(state, payload.locations);
      buildingsAdapter.setAll(state.buildings, payload.buildings);
      devicesAdapter.setAll(state.devices, payload.devices);
    },

    [deleteLocationAsync.pending]: (state) => {
      state.status = "loading";
      state.error = null;
    },
    [deleteLocationAsync.fulfilled]: (state, {payload}) => {
      state.status = "resolved";
      state.error = null;
      const buildings = buildingsAdapter.getSelectors().selectAll(state.buildings);
      const buildingsIds = buildings.filter(({locationId}) => locationId === payload).map(({id}) => id);
      const devices = devicesAdapter.getSelectors().selectAll(state.devices);
      const devicesIds = devices.filter(({buildingId}) => buildingsIds.includes(buildingId)).map(({id}) => id);
      locationsAdapter.removeOne(state, payload);
      buildingsAdapter.removeMany(state.buildings, buildingsIds);
      devicesAdapter.removeMany(state.devices, devicesIds)
    },


    [deleteBuildingAsync.pending]: (state) => {
      state.status = "loading";
      state.error = null;
    },
    [deleteBuildingAsync.fulfilled]: (state, {payload}) => {
      state.status = "resolved";
      state.error = null;
      const building = buildingsAdapter.getSelectors().selectById(state.buildings, payload);
      const devices = devicesAdapter.getSelectors().selectAll(state.devices);
      const devicesIds = devices.filter(({buildingId}) => buildingId === payload).map(({id}) => id);
      buildingsAdapter.removeOne(state.buildings, payload);
      devicesAdapter.removeMany(state.devices, devicesIds);
      const location = locationsAdapter.getSelectors().selectById(state, building.locationId);
      locationsAdapter.updateOne(state, {
        id: location.id,
        changes: {
          buildings: location.buildings.filter(({id}) => id !== building.id)
        },
      });
    },

    [deleteDeviceAsync.pending]: (state) => {
      state.status = "resolved";
      state.error = null;
    },
    [deleteDeviceAsync.fulfilled]: (state, {payload}) => {
      const device = devicesAdapter.getSelectors().selectById(state.devices, payload);
      const building = buildingsAdapter.getSelectors().selectById(state.buildings, device.buildingId);
      buildingsAdapter.updateOne(state.buildings, {
        id: building.id,
        changes: {
          devices: building.devices.filter(({id}) => id !== payload)
        },
      });
      const updatedBuilding = buildingsAdapter.getSelectors().selectById(state.buildings, device.buildingId);
      const location = locationsAdapter.getSelectors().selectById(state, updatedBuilding.locationId)
      locationsAdapter.updateOne(state, {
        id: updatedBuilding.locationId,
        changes: {
          buildings: location.buildings.map((building) =>
            building.id === updatedBuilding.id ? updatedBuilding : building)
        },
      });
      devicesAdapter.removeOne(state.devices, payload);
    },
  }
});
export const {
  removeAllLocations,
  removeAllBuildings,
  removeAllDevices,
  setAllBuildings,

} = structureSlice.actions;

export default structureSlice.reducer;



