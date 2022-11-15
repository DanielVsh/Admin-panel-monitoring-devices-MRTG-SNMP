import React, { useState } from "react";
import table from "../TableStyle.module.css";
import { useCreateNewLocationMutation, useDeleteLocationMutation, useGetLocationsQuery } from "../../../../features/redux/api/structureApi";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import ErrorPage from "../error/ErrorPage"
import { useNavigate } from 'react-router-dom';
import debounce from "lodash.debounce";

const Locations = () => {

  const navigate = useNavigate();

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [sortedElement, setSortedElement] = useState("id");
  const [sortedDirection, setSortedDirection] = useState("desc");
  const [filter, setFilter] = useState("");

  const { data: pageableLocation, isLoading: pageableLoading, isError: pageableError }
    = useGetLocationsQuery({
      page: page,
      sort: {
        element: sortedElement,
        direction: sortedDirection
      },
      size: size,
      filter: filter,
    });
  const [deleteLocation] = useDeleteLocationMutation();
  const [createLocation] = useCreateNewLocationMutation();
  console.log(filter)
  const [name, setName] = useState('');

  const handleCreateLocation = async (body) => {
    await createLocation(body).unwrap();
  }

  const handleDeleteLocation = async (id) => {
    await deleteLocation(id).unwrap();
  }


  if (pageableLoading) return <LoaderHook />
  if (pageableError) return <ErrorPage />

  return (
    <>
      <input name={filter} onChange={debounce((e) => setFilter(e.target.value), 500)} placeholder={"search"} />
      <input name={name} onChange={(e) => setName(e.target.value)} placeholder={"Location name"} />
      <button onClick={(e) => handleCreateLocation({
        name: name,
      }, e.preventDefault())}>Create new Location</button>
      <table className={table.table}>
        <thead className={table.head}>
          <tr>
            <td className={table.minSize}>Id</td>
            <td>Name</td>
            <td className={table.minSize}>Buildings</td>
            <td className={table.minSize}>Devices</td>
            <td className={table.minSize}>Actions</td>
          </tr>
        </thead>
        <tbody>
          {pageableLocation.content.map((location) => (
            <tr key={location.id}>
              <td>{location.id}</td>
              <td>{location.name}</td>
              <td>{location.buildings.length}</td>
              <td>{location.buildings.reduce((sum, building) => sum + building.devices.length, 0)}</td>
              <td>
                <button onClick={() => handleDeleteLocation(location.id)}>Delete</button>
                <button onClick={() => navigate(`location/${location.id}`, { replace: true })}>Edit</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {page + 1 + " of " + pageableLocation.totalPages}
      <button onClick={() => setPage(page > 0 ? page - 1 : page)}>prev page</button>
      <button onClick={() => setPage(pageableLocation.totalPages - 1 > page ? page + 1 : page)}>next page</button>
      <button onClick={() => setPage(0)}>First page</button>
      <button onClick={() => setPage(pageableLocation.totalPages - 1)}>Last page</button>
      <select value={size} onChange={(e) => setSize(e.target.value)} >
        <option value={10}>{10} </option>
        <option value={20}>{20} </option>
        <option value={30}>{30} </option>
        <option value={40}>{40} </option>
      </select>
      {size}
    </>
  )
}

export default Locations;