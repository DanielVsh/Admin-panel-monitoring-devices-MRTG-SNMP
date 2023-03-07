import React, { useState, useEffect } from "react";
import table from "../TableStyle.module.css"
import { useCreateNewDeviceMutation, useDeleteDeviceMutation, useGetBuildingQuery, useGetDeviceQuery, useGetLocationsQuery } from "../../../../features/redux/api/structureApi";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import ErrorPage from "../error/ErrorPage";
import debounce from 'lodash.debounce';
import JSOG from 'jsog';

const Devices = () => {

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [sortedElement, setSortedElement] = useState("id");
  const [sortedDirection, setSortedDirection] = useState("desc");
  const [filterLine, setFilterLine] = useState("");
  const [filter, setFilter] = useState("");

  const { data: devices, isLoading, isError } = useGetDeviceQuery({
    page: page,
    sort: {
      element: sortedElement,
      direction: sortedDirection
    },
    size: size,
    filter: filterLine
  });
  const { data: buildingsData, isLoading: isLoadingBuildings } = useGetBuildingQuery({
    page: 0,
    sort: {
      element: "name",
      direction: "asc"
    },
    size: 10,
    filter: filter,
  });

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

  useEffect(() => {
    if (buildingsData) {
      setBuildingId(buildingsData?.content[0]?.id);
    }
  }, [buildingsData]);

  useEffect(() => {
    if (devices) {
      setPage(0);
    }
  }, [filterLine])

  if (isLoading) return <LoaderHook />
  if (isError) return <ErrorPage />


  return (
    <>
      <input name={filterLine} onChange={debounce((e) => setFilterLine(e.target.value), 500)} placeholder={"search line"} />
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
          {JSOG.decode(devices?.content).map(device => (
            <tr key={device.id}>
              <td>{device.id}</td>
              <td>{device.name}</td>
              <td>{device.building.name}</td>
              <td>{device.building.location.name}</td>
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
      {page + 1 + " of " + devices.totalPages}
      <button onClick={() => setPage(page > 0 ? page - 1 : page)}>prev page</button>
      <button onClick={() => setPage(devices.totalPages - 1 > page ? page + 1 : page)}>next page</button>
      <button onClick={() => setPage(0)}>First page</button>
      <button onClick={() => setPage(devices.totalPages - 1)}>Last page</button>
      <select value={size} onChange={(e) => setSize(e.target.value)} >
        <option value={10}>{10} </option>
        <option value={20}>{20} </option>
        <option value={30}>{30} </option>
        <option value={40}>{40} </option>
      </select>
    </>
  )
}

export default Devices;