import React from "react";
import FetchHook from "../../../hooks/FetchHook";
import table from "../TableStyle.module.css";
import Loading from "react-loading";

const Locations = () => {

    const countDevices = (buildings) => {
        let devices = 0;
        buildings.map(build => {
                devices += build.devices.length
        })
        return devices
    }


    const locations = FetchHook("api/v1/location")
    if (!locations) return <Loading/>


    return (
        <table className={table.table}>
            <thead>
            <tr className={table.head}>
                <td>Id</td>
                <td>Name</td>
                <td>Buildings</td>
                <td>Devices</td>
            </tr>
            </thead>
            <tbody>
            {locations.slice(0).reverse().map((location) => (
                <tr key={location.id}>
                    <td>{location.id}</td>
                    <td>{location.name}</td>
                    <td>{location.buildings !== undefined ? location.buildings.length : "0"}</td>
                    <td>{countDevices(location.buildings)}</td>
                </tr>
            ))}
            </tbody>
        </table>
    )
}

export default Locations;