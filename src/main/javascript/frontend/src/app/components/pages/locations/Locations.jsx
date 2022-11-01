import React from "react";
import table from "../TableStyle.module.css";
import { useDeleteLocationMutation, useGetLocationsQuery } from "../../../../features/redux/api/structureApi";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import ErrorPage from "../error/ErrorPage"

const Locations = () => {

    const {data: locations, isLoading, isError} = useGetLocationsQuery();
    const [deleteLocation] = useDeleteLocationMutation();


    const handleDeleteLocation = async (id) => {
      await deleteLocation(id).unwrap();
    }
  

    if(isLoading) return <LoaderHook/>
    if(isError) return <ErrorPage/>

    return (
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
            {locations && locations.slice(0).reverse().map((location) => (
                <tr key={location.id}>
                    <td>{location.id}</td>
                    <td>{location.name}</td>
                    <td>{location.buildings !== undefined ? location.buildings.length : "0"}</td>
                    <td>?</td>
                    <td>
                        <button onClick={() => handleDeleteLocation(location.id)}>Delete</button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    )
}

export default Locations;