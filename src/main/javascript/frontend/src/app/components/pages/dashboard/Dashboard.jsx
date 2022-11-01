import React from "react";
import dashboard from "./Dashboard.module.css"

const Dashboard = () => {

    return (
        <div className={dashboard}>
            <header>
                <h1>Dashboard</h1>
            </header>
            <div className={dashboard.main}>
                <div className={dashboard.locations}>
                    Locations
                    <div className={dashboard.button}>
                        <button type={"button"}>Button</button>
                    </div>
                </div>
                <div className={dashboard.buildings}>
                    Buildings
                    <div className={dashboard.button}>
                        <button type={"button"}>Button</button>
                    </div>
                </div>
                <div className={dashboard.devices}>
                    Devices
                    <div className={dashboard.button}>
                        <button type={"button"}>Button</button>
                    </div>
                </div>
                <div className={dashboard.connectedDevices}>
                    Connected devices
                    <div className={dashboard.button}>
                        <button type={"button"}>Button</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Dashboard;