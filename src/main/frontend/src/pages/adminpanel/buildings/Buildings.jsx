import React from "react";
import table from "../TableStyle.module.css";
import FetchHook from "../../../hooks/FetchHook";
import LoaderHook from "../../../hooks/loader/LoaderHook";

const Buildings = () => {

    const buildings = FetchHook(`api/v1/building`)
    if (!buildings) return <LoaderHook/>

    return (
        <table className={table.table}>
            <thead>
            <tr className={table.head}>
                <td>Id</td>
                <td>Name</td>
                <td>Devices</td>
            </tr>
            </thead>
            <tbody>
            {buildings.slice(0).reverse().map((building) => (
                <tr key={building.id}>
                    <td>{building.id}</td>
                    <td>{building.name}</td>
                    <td>{building.devices !== undefined ? building.devices.length : "0"}</td>
                </tr>
            ))}
            </tbody>
        </table>
    )
}

export default Buildings;