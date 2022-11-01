import React from "react";
import {NavLink} from "react-router-dom";
import header from '../header/Header.module.css'

const Header = () => {


    return (
        <header className={header.header}>
            <div className={header.main}>
                <div>
                    <NavLink to={"/"}>Main</NavLink>
                    <NavLink to={"/login"}>Login</NavLink>
                    <NavLink to={"/logout"}>Logout</NavLink>
                </div>
                <div className={header.text}>
                </div>
            </div>
        </header>

    )
}
export default Header;