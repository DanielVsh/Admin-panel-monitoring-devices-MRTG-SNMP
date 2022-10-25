import React from "react";
import table from "../TableStyle.module.css"
import FetchHook from "../../../hooks/FetchHook";
import LoaderHook from "../../../hooks/loader/LoaderHook";

const Devices = () => {

    const devices = FetchHook(`api/v1/device`)
    if (!devices) return <LoaderHook/>

    return (
        <table className={table.table}>
            <thead>
            <tr className={table.head}>
                <td>Id</td>
                <td>Name</td>
                <td>Ip</td>
                <td>UpTime</td>
                <td>SwitchMap</td>
            </tr>
            </thead>
            <tbody>
            {devices.slice(0).reverse().map((device) => (
                <tr key={device.id}>
                    <td>{device.id}</td>
                    <td>{device.name}</td>
                    <td>{device.ipAddress}</td>
                    <td>{device.uptime}</td>
                    <td>{device.switchmap ? "true" : "false"}</td>
                </tr>
            ))}
            </tbody>
        </table>
    )
}

export default Devices;