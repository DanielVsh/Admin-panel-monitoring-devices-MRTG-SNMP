import React, {useEffect} from "react";
import dashboard from "./Dashboard.module.css"
import {useDispatch, useSelector} from "react-redux";
import {
    buildingsSelectors, devicesSelectors,
    fetchStructureAsync,
    locationsSelectors
} from "../../../../features/redux/reducers/structureSlice";

const Dashboard = () => {

    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(fetchStructureAsync());
    },[]);

    return (
        <div className={dashboard}>
            <header>
                <h1>Dashboard</h1>
            </header>
            <div className={dashboard.main}>
                <div className={dashboard.locations}>
                    Locations
                    <div>
                        {useSelector(locationsSelectors.selectTotal)}
                    </div>
                    <div className={dashboard.button}>
                        <button type={"button"}>Button</button>
                    </div>
                </div>
                <div className={dashboard.buildings}>
                    Buildings
                    <div>
                        {useSelector(buildingsSelectors.selectTotal)}
                    </div>
                    <div className={dashboard.button}>
                        <button type={"button"}>Button</button>
                    </div>
                </div>
                <div className={dashboard.devices}>
                    Devices
                    <div>
                        {useSelector(devicesSelectors.selectTotal)}
                    </div>
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