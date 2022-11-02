import React from "react";
import { NavLink } from "react-router-dom";
import { useGetAdminQuery } from "../../../../features/redux/api/authApi";
import header from '../header/Header.module.css'

const Header = () => {

    const {data: admin} = useGetAdminQuery({
        skip: true
    })

    return (
        <header className={header.header}>
            <div className={header.main}>
                <div>
                    <NavLink to={"/"}>Main</NavLink>
                    <NavLink to={"/login"}>Login</NavLink>
                    <NavLink to={"/logout"}>Logout</NavLink>
                </div>
                <div className={header.text}>
                    {admin && admin.username}
                </div>
            </div>
        </header>

    )
}
export default Header;