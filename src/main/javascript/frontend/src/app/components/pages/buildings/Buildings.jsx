import React, { useState, useEffect } from "react";
import table from "../TableStyle.module.css";
import { useCreateNewBuildingMutation, useDeleteBuildingMutation, useGetBuildingQuery, useGetLocationsQuery } from "../../../../features/redux/api/structureApi";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import ErrorPage from "../error/ErrorPage"
import { useNavigate } from 'react-router-dom';
import debounce from 'lodash.debounce';

const Buildings = () => {

  const navigate = useNavigate();


  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [sortedElement, setSortedElement] = useState("id");
  const [sortedDirection, setSortedDirection] = useState("desc");
  const [filterLine, setFilterLine] = useState("");
  const [filter, setFilter] = useState("");

  const { data: pageableBuilding, isLoading: pageableLoading, isError: pageableError }
    = useGetBuildingQuery({
      page: page,
      sort: {
        element: sortedElement,
        direction: sortedDirection
      },
      size: size,
      filter: filterLine
    });


    const { data: pageableLocation } = useGetLocationsQuery({
      page: 0,
      sort: {
        element: "name",
        direction: "asc"
      },
       size: 10,
      filter: filter,
    });
  const [deleteBuilding] = useDeleteBuildingMutation();
  const [createBuilding] = useCreateNewBuildingMutation();


  const handleCreateBuilding = async (body) => {
    await createBuilding(body).unwrap();
  }
  const handleDeleteBuilding = async (id) => {
    await deleteBuilding(id).unwrap();
  }


  const [name, setName] = useState('');
  const [locationId, setLocationId] = useState(Number);


  useEffect(() => {
    if (pageableLocation) {
      setLocationId(pageableLocation?.content[0]?.id)
    }
  }, [pageableLocation]);


  if (pageableLoading) return <LoaderHook />
  if (pageableError) return <ErrorPage />

  return (
    <>
      <input name={filterLine} onChange={debounce((e) => setFilterLine(e.target.value), 500)} placeholder={"search line"} />
      <form>
        <input name={"name"} onChange={(e) => setName(e.target.value)} placeholder={"Building name"} />
        <input name={filter} onChange={debounce((e) => setFilter(e.target.value), 500)} placeholder={"search"} />
        <select onChange={(e) => setLocationId(e.target.value)}>
          {pageableLocation?.content?.map(location => (
            <option key={location.id} value={location.id}>id: {location.id} name: {location.name}</option>
          ))}
        </select>{locationId}<br />
        <button onClick={(e) => handleCreateBuilding({
          name: name,
          locationId: locationId
        }, e.preventDefault())}>Create new Building</button>
      </form>
      <table className={table.table}>
        <thead className={table.head}>
          <tr>
            <td className={table.minSize}>Id</td>
            <td>Name</td>
            <td>Location</td>
            <td>Devices</td>
            <td className={table.minSize}>Actions</td>
          </tr>
        </thead>
        <tbody>
          {pageableBuilding.content.map((building) => (
            <tr key={building.id}>
              <td>{building.id}</td>
              <td>{building.name}</td>
              <td>?</td>
              <td>{building.devices ? building.devices.length : "0"}</td>
              <td>
                <button onClick={() => handleDeleteBuilding(building.id)}>Delete</button>
                <button onClick={() => navigate(`building/${building.id}`, { replace: true })}>Edit</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {page + 1 + " of " + pageableBuilding.totalPages}
      <button onClick={() => setPage(page > 0 ? page - 1 : page)}>prev page</button>
      <button onClick={() => setPage(pageableBuilding.totalPages - 1 > page ? page + 1 : page)}>next page</button>
      <button onClick={() => setPage(0)}>First page</button>
      <button onClick={() => setPage(pageableBuilding.totalPages - 1)}>Last page</button>
      <select value={size} onChange={(e) => setSize(e.target.value)} >
        <option value={10}>{10} </option>
        <option value={20}>{20} </option>
        <option value={30}>{30} </option>
        <option value={40}>{40} </option>
      </select>
    </>
  )
}

export default Buildings;