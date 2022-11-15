import React from "react";
import {Outlet} from "react-router-dom";
import jwt_decode from 'jwt-decode';

const AuthRouteWrite = () => {
    let userInfo = {
        subject: null,
        isRole: false
    }
    const ADMIN_WRITE = "ADMIN:WRITE"

    if(localStorage.getItem("access_token")) {
        const token_info = jwt_decode(localStorage.getItem("access_token"));
        userInfo.subject = token_info.subject;
        userInfo.isRole = token_info.roles.some(role => role === ADMIN_WRITE);
    }

    return (
           !localStorage.getItem("access_token")
        && !localStorage.getItem("refresh_token")
        && !userInfo.subject
        && !userInfo.isRole
    ) ? null
    : <Outlet/>
};

export default AuthRouteWrite;