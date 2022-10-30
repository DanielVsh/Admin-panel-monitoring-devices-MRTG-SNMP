import React from "react";
import {useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";
import {removeAllUsers, userSelectors} from "../../../features/redux/reducers/userSlice";
import {
    buildingsSelectors,
    devicesSelectors,
    locationsSelectors,
    removeAllBuildings,
    removeAllDevices,
    removeAllLocations
} from "../../../features/redux/reducers/structureSlice";

const Logout = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const clearData = () => {
        localStorage.clear();
        dispatch(removeAllUsers(userSelectors));
        dispatch(removeAllLocations(locationsSelectors));
        dispatch(removeAllBuildings(buildingsSelectors));
        dispatch(removeAllDevices(devicesSelectors));
    };

    const logout = () => {
        clearData();
        navigate("/", {replace: true});
    }
    return (
        <div>
            <h1>Welcome to logout page</h1>
            <button type={"submit"} onClick={() => logout()}>Logout</button>
        </div>
    )
}

export default Logout;