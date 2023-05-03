import debounce from "lodash.debounce";
import React, {useEffect, useState} from "react";
import {useCreateNewDeviceMutation, useGetBuildingQuery} from "../../../../features/redux/api/structureApi";

export const DeviceCreate = () => {
  const [name, setName] = useState('');
  const [ipAddress, setIpAddress] = useState('');
  const [switchMap, setSwitchMap] = useState(Boolean);
  const [SNMP, setSNMP] = useState('');
  const [buildingId, setBuildingId] = useState(1);

  const [filter, setFilter] = useState("");

  const [createDevice] = useCreateNewDeviceMutation();
  const handleCreateDevice = async (body) => {
    await createDevice(body).unwrap();
  }

  const { data: buildingsData, isLoading: isLoadingBuildings } = useGetBuildingQuery({
    page: 0,
    sort: {
      element: "id",
      direction: "asc"
    },
    size: 10,
    filter: filter,
  });


  useEffect(() => {
    if (buildingsData) {
      setBuildingId(buildingsData?.content[0]?.id);
    }
  }, [buildingsData]);

  return (
    <>
      <form>
        <input name={"name"} value={name} onChange={(e) =>
          setName(e.target.value)} placeholder={"Device name"} />
        <input name={"ipAddress"} value={ipAddress} onChange={(e) =>
          setIpAddress(e.target.value)} required placeholder={"Device ip"} />
        <select name={"switchMap"} value={switchMap} onChange={(e) => setSwitchMap(e.target.value)} >
          <option value={true}>TRUE</option>
          <option value={false}>FALSE</option>
        </select>
        <input name={"SNMP"} value={SNMP} onChange={(e) =>
          setSNMP(e.target.value)} placeholder={"SNMP community"} />
        <input name={filter} onChange={debounce((e) => setFilter(e.target.value), 500)} placeholder={"search"} />
        <select value={buildingId} onChange={(e) => setBuildingId(e.target.value)} >
          {buildingsData?.content?.map(building => (
            <option key={building.id} value={building.id}>id: {building.id} name: {building.name}</option>
          ))}
        </select>
        {buildingId}
        <button onClick={(e) => handleCreateDevice({
          name: name,
          ipAddress: ipAddress,
          uptime: null,
          switchMap: switchMap,
          snmp: SNMP,
          buildingId: buildingId
        }, e.preventDefault())}>Create new Device</button>
      </form>
    </>
  )
}