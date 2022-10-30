import React from "react";
import table from "../TableStyle.module.css";
import {useDispatch, useSelector} from "react-redux";
import {
  buildingsSelectors,
  deleteBuildingAsync,
  locationsSelectors
} from "../../../../features/redux/reducers/structureSlice";

const Buildings = () => {
  const dispatch = useDispatch();
  const buildings = useSelector(state => buildingsSelectors.selectAll(state));

  return (
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
      {buildings.slice(0).reverse().map((building) => (
        <tr key={building.id}>
          <td>{building.id}</td>
          <td>{building.name}</td>
          <td>{useSelector(state => locationsSelectors.selectById(state, building.locationId).name)}</td>
          <td>{building.devices !== undefined ? building.devices.length : "0"}</td>
          <td>
            <button onClick={() => dispatch(deleteBuildingAsync(building.id))}>Delete</button>
          </td>
        </tr>
      ))}
      </tbody>
    </table>
  )
}

export default Buildings;