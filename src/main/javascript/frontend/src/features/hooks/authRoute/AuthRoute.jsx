import React from "react";
import {Navigate, Outlet} from "react-router-dom";
import jwt_decode from 'jwt-decode';

const AuthRoute = () => {

    let userInfo = {
        subject: null,
        isRole: false
    }

    const ADMIN_VIEW = "ADMIN:VIEW";
    const ADMIN_WRITE = "ADMIN:WRITE"

    // const user = useSelector(state => userSelectors.selectById(state, 1))
    // if(!user) return <Navigate to={"/login"} replace={true}/>

    if(localStorage.getItem("access_token")) {
        const token_info = jwt_decode(localStorage.getItem("access_token"));
        userInfo.subject = token_info.subject;
        userInfo.isRole = token_info.roles.some(role => role === ADMIN_VIEW);
    }
    return (
           !localStorage.getItem("access_token")
        && !localStorage.getItem("refresh_token")
        // && !user.isLogged
        && !userInfo.subject
        && !userInfo.isRole
    ) ? <Navigate to={"/login"} replace={true}/> : <Outlet/>

};

export default AuthRoute;