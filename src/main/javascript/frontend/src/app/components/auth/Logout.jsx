import React from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from 'react-router-dom';
import { structureApi } from "../../../features/redux/api/structureApi";
import { authApi } from './../../../features/redux/api/authApi';

const Logout = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    
    const clearData = () => {
        localStorage.clear();
        dispatch(structureApi.util.resetApiState());
        dispatch(authApi.util.resetApiState());
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