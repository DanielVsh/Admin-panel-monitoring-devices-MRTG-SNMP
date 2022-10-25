import React from "react";
import {Outlet} from "react-router-dom";
import FetchHook from "../hooks/FetchHook";

const AuthRoute = () => {

    const data = FetchHook("api/v1/auth")

    return data
        ? <Outlet/>
        : ""

};

export default AuthRoute;