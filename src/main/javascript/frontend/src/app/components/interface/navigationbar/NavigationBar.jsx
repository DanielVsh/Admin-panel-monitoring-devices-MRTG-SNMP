import React from "react";
import {NavLink} from "react-router-dom";
import navbar from "./NavBar.module.css"

const NavBar = () => {
    return (
        <nav className={navbar.navbar}>
            <div>
                <NavLink end={"/dashboard"} to={"/dashboard"} className={props => props.isActive ? navbar.active : navbar.passive}>Dashboard</NavLink>
            </div>
            <div>
                <NavLink to={"/dashboard/locations"} className={props => props.isActive ? navbar.active : navbar.passive}>Locations</NavLink>
            </div>
            <div>
                <NavLink to={"/dashboard/buildings"} className={props => props.isActive ? navbar.active : navbar.passive}>Buildings</NavLink>
            </div>
            <div>
                <NavLink to={"/dashboard/devices"} className={props => props.isActive ? navbar.active : navbar.passive}>Devices</NavLink>
            </div>
            <div>
                <NavLink to={"/dashboard/logs"} className={props => props.isActive ? navbar.active : navbar.passive}>Logs</NavLink>
            </div>
        </nav>
    )
}

export default NavBar;