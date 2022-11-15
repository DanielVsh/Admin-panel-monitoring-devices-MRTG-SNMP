import React, { useState } from "react";
import table from "../TableStyle.module.css"
import { useCreateNewDeviceMutation, useDeleteDeviceMutation, useGetBuildingQuery, useGetDeviceQuery, useGetLocationsQuery } from "../../../../features/redux/api/structureApi";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import ErrorPage from "../error/ErrorPage";

const Devices = () => {

  const { data: devices, isLoading, isError } = useGetDeviceQuery();
  const { data: locationsData, isLoading: isLoadingLocations} = useGetLocationsQuery();
  const { data: buildingsData, isLoading: isLoadingBuildings} = useGetBuildingQuery();
  const [deleteDevice] = useDeleteDeviceMutation();
  const [createDevice] = useCreateNewDeviceMutation();

  const [name, setName] = useState('');
  const [ipAddress, setIpAddress] = useState('');
  const [switchMap, setSwitchMap] = useState(Boolean);
  const [SNMP, setSNMP] = useState('');
  const [buildingId, setBuildingId] = useState(1);

  const handleDeleteDevice = async (id) => {
    await deleteDevice(id).unwrap();
  }

  const handleCreateDevice = async (body) => {
    await createDevice(body).unwrap();
  }


  if (isLoading || isLoadingLocations || isLoadingBuildings) return <LoaderHook />
  if (isError) return <ErrorPage />

  return (
    <>
      <form>
        <input name={"name"} value={name} onChange={(e) =>
          setName(e.target.value)}  placeholder={"Device name"} />
        <input name={"ipAddress"} value={ipAddress} onChange={(e) =>
          setIpAddress(e.target.value)} required placeholder={"Device ip"} />
        <select name={"switchMap"} value={switchMap} onChange={(e) => setSwitchMap(e.target.value)} >
            <option value={true}>TRUE</option>
            <option value={false}>FALSE</option>
          </select>
        <input name={"SNMP"} value={SNMP} onChange={(e) =>
          setSNMP(e.target.value)}  placeholder={"SNMP community"} />
        <select value={buildingId} onChange={(e) => setBuildingId(e.target.value)} >
        <option value = "" hidden> choose the building </option>
        {buildingsData && buildingsData.map( building => (
          <option key={building.id} value={building.id}>{building.name}</option>
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
        },e.preventDefault())}>Create new Device</button>
      </form>

      <table className={table.table}>
        <thead className={table.head}>
          <tr>
            <td className={table.minSize}>Id</td>
            <td>Name</td>
            <td>Building</td>
            <td>Location</td>
            <td className={table.minSize}>Ip</td>
            <td className={table.minSize}>UpTime</td>
            <td className={table.minSize}>SNMP</td>
            <td className={table.minSize}>SwitchMap</td>
            <td className={table.minSize}>Actions</td>
          </tr>
        </thead>
        <tbody>
          {devices.slice(0).reverse().map(device => (
            <tr key={device.id}>
              <td>{device.id}</td>
              <td>{device.name}</td>
              <td>?</td>
              <td>?</td>
              <td>{device.ipAddress}</td>
              <td>{device.uptime}</td>
              <td>{device.snmp}</td>
              <td>{device.switchMap ? "true" : "false"}</td>
              <td>
                <button onClick={() => handleDeleteDevice(device.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  )
}

export default Devices;