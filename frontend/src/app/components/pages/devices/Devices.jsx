import React, {useEffect, useState} from "react";
import table from "../TableStyle.module.css"
import {useDeleteDeviceMutation, useGetDeviceQuery} from "../../../../features/redux/api/structureApi";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import ErrorPage from "../error/ErrorPage";
import debounce from 'lodash.debounce';
import JSOG from 'jsog';
import {useNavigate} from "react-router-dom";

const Devices = () => {

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [sortedElement, setSortedElement] = useState("id");
  const [sortedDirection, setSortedDirection] = useState("desc");
  const [filterLine, setFilterLine] = useState("");

  const navigate = useNavigate();

  const {data: devices, isLoading, isError} = useGetDeviceQuery({
    page: page,
    sort: {
      element: sortedElement,
      direction: sortedDirection
    },
    size: size,
    filter: filterLine
  });

  const [deleteDevice] = useDeleteDeviceMutation();

  const handleDeleteDevice = async (id) => {
    await deleteDevice(id).unwrap();
  }

  useEffect(() => {
    if (devices) {
      setPage(0);
    }
  }, [filterLine])

  if (isLoading) return <LoaderHook/>
  if (isError) return <ErrorPage/>


  return (
    <>
      <div style={{display: "flex", justifyContent: "space-between"}}>
        <input name={filterLine} onChange={debounce((e) => setFilterLine(e.target.value), 500)}
               placeholder={"search line"}/>
        <i style={{paddingRight: "5px", paddingTop: "2px"}} className="bi bi-plus-square-fill" onClick={() => navigate(`create`, {replace: true})}></i>
      </div>
      <table className={table.table}>
        <thead className={table.head}>
        <tr>
          <td onClick={() => {
            setSortedElement('id')
            setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
          }} className={table.minSize}>Id
          </td>
          <td onClick={() => {
            setSortedElement('name')
            setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
          }}>Name
          </td>
          <td onClick={() => {
            setSortedElement('building_name')
            setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
          }}>Building
          </td>
          <td onClick={() => {
            setSortedElement('building_location_name')
            setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
          }}>Location
          </td>
          <td onClick={() => {
            setSortedElement('ipAddress')
            setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
          }} className={table.minSize}>Ip
          </td>
          <td onClick={() => {
            setSortedElement('uptime')
            setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
          }}>UpTime
          </td>
          <td onClick={() => {
            setSortedElement('SNMP')
            setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
          }} className={table.minSize}>SNMP
          </td>
          <td onClick={() => {
            setSortedElement('switchMap')
            setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
          }} className={table.minSize}>SwitchMap
          </td>
          <td className={table.actionSize}>Actions</td>
        </tr>
        </thead>
        <tbody>
        {JSOG.decode(devices?.content).map(device => {
          const deviceTime = new Date(device?.uptime);
          const currentTime = new Date();
          const timeDiff = Math.abs(currentTime - deviceTime);
          const days = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
          const hours = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)).toString().padStart(2, '0');
          const minutes = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60)).toString().padStart(2, '0');
          const seconds = Math.floor((timeDiff % (1000 * 60)) / 1000).toString().padStart(2, '0');

          return (
            <tr key={device?.id}>
              <td>{device?.id}</td>
              <td>{device?.name}</td>
              <td>{device?.building?.name}</td>
              <td>{device?.building?.location?.name}</td>
              <td>{device?.ipAddress}</td>
              <td>{`${days} days ${hours}:${minutes}:${seconds} hours`}</td>
              <td>{device?.snmp}</td>
              <td>{device?.switchMap ? "true" : "false"}</td>
              <td className={table.action}>
                <i className="bi bi-pencil-fill" onClick={() => navigate(`device/${device.id}`, {replace: true})}></i>
                <i className="bi bi-trash" onClick={() => handleDeleteDevice(device.id)}></i>
              </td>
            </tr>
          );
        })}
        </tbody>
      </table>
      {page + 1 + " of " + devices.totalPages}
      <button onClick={() => setPage(page > 0 ? page - 1 : page)}>prev page</button>
      <button onClick={() => setPage(devices.totalPages - 1 > page ? page + 1 : page)}>next page</button>
      <button onClick={() => setPage(0)}>First page</button>
      <button onClick={() => setPage(devices.totalPages - 1)}>Last page</button>
      <select key={1} value={size} onChange={(e) => setSize(e.target.value)}>
        <option value={10}>{10} </option>
        <option value={20}>{20} </option>
        <option value={30}>{30} </option>
        <option value={40}>{40} </option>
      </select>
    </>
  )
}

export default Devices;