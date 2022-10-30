import React from "react";
import table from "../TableStyle.module.css"
import {useDispatch, useSelector} from "react-redux";
import {
  buildingsSelectors,
  deleteDeviceAsync,
  devicesSelectors, locationsSelectors
} from "../../../../features/redux/reducers/structureSlice";

const Devices = () => {

  const devices = useSelector(devicesSelectors.selectAll);
  const dispatch = useDispatch();


  return (
    <table className={table.table}>
      <thead className={table.head}>
      <tr>
        <td className={table.minSize}>Id</td>
        <td>Name</td>
        <td>Building</td>
        <td>Location</td>
        <td className={table.minSize}>Ip</td>
        <td className={table.minSize}>UpTime</td>
        <td className={table.minSize}>SwitchMap</td>
        <td className={table.minSize}>Actions</td>
      </tr>
      </thead>
      <tbody>
      {devices
        .slice(0).reverse().map(device => (
          <tr key={device.id}>
            <td>{device.id}</td>
            <td>{device.name}</td>
            <td>{useSelector(state => buildingsSelectors.selectById(state, device.buildingId).name)}</td>
            <td>{useSelector(state => locationsSelectors.selectById(state, device.locationId).name)}</td>
            <td>{device.ipAddress}</td>
            <td>{device.uptime}</td>
            <td>{device.switchMap ? "true" : "false"}</td>
            <td>
              <button onClick={() => dispatch(deleteDeviceAsync(device.id))}>Delete</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  )
}

export default Devices;