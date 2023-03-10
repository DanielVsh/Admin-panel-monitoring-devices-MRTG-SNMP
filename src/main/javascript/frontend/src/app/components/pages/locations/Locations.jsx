import React, { useState, useEffect, useRef, useCallback, useMemo } from "react";
import table from "../TableStyle.module.css";
import { useCreateNewLocationMutation, useDeleteLocationMutation, useGetLocationsQuery } from "../../../../features/redux/api/structureApi";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import ErrorPage from "../error/ErrorPage"
import { useNavigate } from 'react-router-dom';
import debounce from "lodash.debounce";
import JSOG from "jsog";
import 'bootstrap-icons/font/bootstrap-icons.css';

const Locations = () => {

  const navigate = useNavigate();

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [sortedElement, setSortedElement] = useState("id");
  const [sortedDirection, setSortedDirection] = useState("desc");
  const [filterLine, setFilterLine] = useState("");
  const [name, setName] = useState('');
  const [dropdownCreateLocation, setDropdownCreateLocation] = useState(false);

  const { data: pageableLocation, isLoading: pageableLoading, isError: pageableError }
    = useGetLocationsQuery(useMemo(
      () => ({
        page,
        sort: {
          element: sortedElement,
          direction: sortedDirection,
        },
        size,
        filter: filterLine,
      }),
      [page, sortedElement, sortedDirection, size, filterLine]
    ));
  const [deleteLocation] = useDeleteLocationMutation();
  const [createLocation] = useCreateNewLocationMutation();

  const handleCreateLocation = async (body) => {
    await createLocation(body).unwrap();
  }

  const handleDeleteLocation = async (id) => {
    await deleteLocation(id).unwrap();
  }

  const modalWindowRef = useRef();
  const handleClick = useCallback(
    (e) => {
      if (dropdownCreateLocation && !modalWindowRef?.current?.contains(e?.target)) {
        setDropdownCreateLocation(false);
      }
    },
    [dropdownCreateLocation]
  );


  useEffect(() => {
    if (pageableLocation) {
      setPage(0);
    };
  }, [filterLine]);

  useEffect(() => {
    document.addEventListener("mousedown", handleClick);
    return () => {
      document.removeEventListener("mousedown", handleClick);
    };
  }, [handleClick]);

  const locations = useMemo(() => JSOG.decode(pageableLocation?.content), [pageableLocation]);
  

  if (pageableLoading) return <LoaderHook />
  if (pageableError) return <ErrorPage />
  console.log(pageableLocation && locations)

  return (
    <>
      <div className={table.topMenu}>
        <input name={filterLine} onChange={debounce((e) => setFilterLine(e.target.value), 500)} placeholder={"search"} />
        {!dropdownCreateLocation &&
          <button onClick={() => setDropdownCreateLocation(!dropdownCreateLocation)} >add new location</button>
        }
      </div>
      {dropdownCreateLocation &&
        <div className={table.newLocation} ref={modalWindowRef}>
          <form>
            <p>Create form</p>
            <div>
              <input name={name} onChange={(e) => setName(e.target.value)} placeholder={"Location name"} />
            </div>
            <div>
              <button onClick={(e) => {
                handleCreateLocation({ name: name }, e.preventDefault())
                setDropdownCreateLocation(false)
              }}>Create</button>
            </div>
          </form>
        </div>
      }

      <table className={table.table}>
        <thead className={table.head}>
          <tr>
            <td onClick={() => {
              setSortedElement("id")
              setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
            }} className={table.minSize}>Id</td>
            <td onClick={() => {
              setSortedElement("name")
              setSortedDirection(sortedDirection === "asc" ? "desc" : "asc")
            }}>Name</td>
            <td className={table.minSize}>Buildings</td>
            <td className={table.minSize}>Devices</td>
            <td className={table.actionSize}>Actions</td>
          </tr>
        </thead>
        <tbody>
          {locations?.map((location, id) => (
            <tr key={id}>
              <td>{location.id}</td>
              <td>{location.name}</td>
              <td>{location.buildings.length}</td>
              <td>{location.buildings.reduce((sum, building) => sum + building.devices.length, 0)}</td>
              <td className={table.action}>
                <i class="bi bi-pencil-fill" onClick={() => navigate(`location/${location.id}`, { replace: true })}></i>
                <i class="bi bi-trash" onClick={() => handleDeleteLocation(location.id)}></i>
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