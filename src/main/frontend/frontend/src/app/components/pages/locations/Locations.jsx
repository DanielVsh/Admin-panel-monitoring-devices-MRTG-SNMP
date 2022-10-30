import React from "react";
import table from "../TableStyle.module.css";
import {useDispatch, useSelector} from "react-redux";
import {
    buildingsSelectors,
    deleteLocationAsync,
    devicesSelectors,
    locationsSelectors, removeBuildingsByLocationId
} from "../../../../features/redux/reducers/structureSlice";

const Locations = () => {

    const dispatch = useDispatch();
    const locations = useSelector(locationsSelectors.selectAll);

    const getDeviceCount = (locationId) => {
      const devices = useSelector(devicesSelectors.selectAll);

      return devices.filter(value => value.locationId === locationId).length;
    }

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
            {locations.slice(0).reverse().map((location) => (
                <tr key={location.id}>
                    <td>{location.id}</td>
                    <td>{location.name}</td>
                    <td>{location.buildings !== undefined ? location.buildings.length : "0"}</td>
                    <td>{getDeviceCount(location.id)}</td>
                    <td>
                        <button onClick={() => dispatch(deleteLocationAsync(location.id))}>Delete</button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    )
}

export default Locations;