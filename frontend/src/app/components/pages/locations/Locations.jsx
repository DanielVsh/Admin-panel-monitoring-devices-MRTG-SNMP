import React, {useEffect, useState} from "react";
import table from "../TableStyle.module.css";
import {useDeleteLocationMutation, useGetLocationsQuery} from "../../../../features/redux/api/structureApi";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import ErrorPage from "../error/ErrorPage"
import {useNavigate} from 'react-router-dom';
import debounce from "lodash.debounce";
import JSOG from "jsog";
import 'bootstrap-icons/font/bootstrap-icons.css';
import {PageNavigation} from "../../../../features/hooks/PageNavigation";

const Locations = () => {

  const navigate = useNavigate();

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [sortedElement, setSortedElement] = useState("id");
  const [sortedDirection, setSortedDirection] = useState("desc");
  const [filterLine, setFilterLine] = useState("");

  const {data: pageableLocation, isLoading: pageableLoading, isError: pageableError}
    = useGetLocationsQuery({
    page,
    sort: {
      element: sortedElement,
      direction: filterLine.length > 0 ? "asc" : sortedDirection,
    },
    size,
    filter: filterLine,
  })
  const [deleteLocation] = useDeleteLocationMutation();


  const handleDeleteLocation = async (id) => {
    await deleteLocation(id).unwrap();
  }

  useEffect(() => {
    if (pageableLocation) {
      setPage(0);
    }
  }, [filterLine]);

  const locations = JSOG.decode(pageableLocation?.content)

  if (pageableLoading) return <LoaderHook/>
  if (pageableError) return <ErrorPage/>

  return (
    <>
      <div className={table.topLine}>
        <input name={filterLine} onChange={debounce((e) => setFilterLine(e.target.value), 500)}
               placeholder={"Search"}/>
        <i className={"bi bi-plus-square-fill"} onClick={() => navigate(`create`, {replace: true})}></i>
      </div>

      <table className={table.table}>
        <thead className={table.head}>
        <tr>
          <td onClick={() => {
            setSortedElement("id")
            setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
          }} className={table.minSize}>Id
          </td>
          <td onClick={() => {
            setSortedElement("name")
            setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
          }}>Name
          </td>
          <td onClick={() => {
            setSortedElement("buildings")
            setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
          }} className={table.minSize}>Buildings
          </td>
          <td className={table.minSize}>Devices</td>
          <td className={table.actionSize}>Actions</td>
        </tr>
        </thead>
        <tbody>
        {locations?.map((location) => (
          <tr key={location.id}>
            <td>{location.id}</td>
            <td>{location.name}</td>
            <td>{location.buildings.length}</td>
            <td>{location.buildings.reduce((sum, building) => sum + building.devices.length, 0)}</td>
            <td className={table.action}>
              <i className="bi bi-pencil-fill" onClick={() => navigate(`location/${location.id}`, {replace: true})}></i>
              <i className="bi bi-trash" onClick={() => handleDeleteLocation(location.id)}></i>
            </td>
          </tr>
        ))}
        </tbody>
      </table>
      <div className={table.pageable}>
        <select value={size} onChange={(e) => setSize(e.target.value)}>
          <option value={10}>{10} </option>
          <option value={20}>{20} </option>
          <option value={30}>{30} </option>
          <option value={40}>{40} </option>
        </select>
        <PageNavigation setPage={setPage} page={page} pageable={pageableLocation}/>
      </div>
    </>
  )
}

export default Locations;