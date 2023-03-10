import React from "react";
import { NavLink } from "react-router-dom";
import jwt_decode from 'jwt-decode';
import header from '../header/Header.module.css'

const Header = () => {

 

    return (
        <header className={header.header}>
            <div className={header.main}>
                <div>
                    <NavLink to={"/"}>Main</NavLink>
                    {localStorage.getItem("access_token") !== null ?? <NavLink to={"/login"}>Login</NavLink>}
                    <NavLink to={"/logout"}>Logout</NavLink>
                </div>
                <div className={header.text}>
                    {jwt_decode(localStorage.getItem("access_token"))?.sub}
                </div>
            </div>
        </header>

    )
}
export default Header;