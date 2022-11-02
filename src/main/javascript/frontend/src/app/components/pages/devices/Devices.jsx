import React from "react";
import table from "../TableStyle.module.css"
import { useCreateNewDeviceMutation, useDeleteDeviceMutation, useGetDeviceQuery } from "../../../../features/redux/api/structureApi";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import ErrorPage from "../error/ErrorPage";


const Devices = () => {

  const { data: devices, isLoading, isError } = useGetDeviceQuery();
  const [deleteDevice] = useDeleteDeviceMutation();
  const [createDevice] = useCreateNewDeviceMutation();

  const handleDeleteDevice = async (id) => {
    await deleteDevice(id).unwrap();
  }

  const handleCreateDevice = async (body) => {
    await createDevice(body).unwrap();
  }

  if (isLoading) return <LoaderHook />
  if (isError) return <ErrorPage />



  return (
    <>
      <div>
        <button onClick={() => handleCreateDevice({
          id: 1,
          name: "sdadadsasdads",
          ipAddress: "123.132.213.132",
          uptime: null,
          switchMap: false,
          snmp: "PRIVATE",
          buildingId: 1
        })}>Create new Device</button>
      </div>

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
          {devices && devices.slice(0).reverse().map(device => (
            <tr key={device.id}>
              <td>{device.id}</td>
              <td>{device.name}</td>
              <td>{device.building && device.building.name }</td>
              <td>{device.building && device.building.location && device.building.location.name}</td>
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