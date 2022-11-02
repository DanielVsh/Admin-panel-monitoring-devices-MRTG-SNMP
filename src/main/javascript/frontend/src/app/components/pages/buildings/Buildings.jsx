import React from "react";
import table from "../TableStyle.module.css";
import { useCreateNewBuildingMutation, useDeleteBuildingMutation, useGetBuildingQuery } from "../../../../features/redux/api/structureApi";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import ErrorPage from "../error/ErrorPage"

const Buildings = () => {

  const { data: buildings, isLoading, isError } = useGetBuildingQuery();
  const [deleteBuilding] = useDeleteBuildingMutation();


  const [createBuilding] = useCreateNewBuildingMutation();

  const handleCreateBuilding = async (body) => {
    await createBuilding(body).unwrap();
  }


  const handleDeleteBuilding = async (id) => {
    await deleteBuilding(id).unwrap();
  }


  if (isLoading) return <LoaderHook />
  if (isError) return <ErrorPage />

  return (
    <>
    <div>
        <button onClick={() => handleCreateBuilding({
          id: 1,
          name: "BuildingTest",
          devicesIds: [3,4,5],
          locationId: 1
        })}>Create new Device</button>
      </div>
      <table className={table.table}>
        <thead className={table.head}>
          <tr>
            <td className={table.minSize}>Id</td>
            <td>Name</td>
            <td>Location</td>
            <td className={table.minSize}>Devices</td>
            <td className={table.minSize}>Actions</td>
          </tr>
        </thead>
        <tbody>
          {buildings && buildings.slice(0).reverse().map((building) => (
            <tr key={building.id}>
              <td>{building.id}</td>
              <td>{building.name}</td>
              <td>{building.location && building.location.name}</td>
              <td>{building.devices !== undefined ? building.devices.length : "0"}</td>
              <td>
                <button onClick={() => handleDeleteBuilding(building.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  )
}

export default Buildings;