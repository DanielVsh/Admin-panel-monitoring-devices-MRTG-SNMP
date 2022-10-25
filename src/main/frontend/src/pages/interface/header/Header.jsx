import React from "react";
import {NavLink} from "react-router-dom";
import header from '../header/Header.module.css'
import {useSelector} from "react-redux";

const Header = () => {
    const admin = useSelector(state => state.auth.admin)

    const getAuthName = () => {
        let name = "user";
        if (admin !== undefined) {
            name = admin.username
        }
        return name;
    }

    return (
        <header className={header.header}>
            <div className={header.main}>
                <div>
                    <NavLink to={"/"}>Main</NavLink>
                    <NavLink to={"/login"}>Login</NavLink>
                    <NavLink to={"/logout"}>Logout</NavLink>
                </div>
                <div className={header.text}>
                    Hello: {getAuthName()}
                </div>
            </div>
        </header>

    )
}
export default Header;