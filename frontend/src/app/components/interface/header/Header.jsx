import React from "react";
import { NavLink } from "react-router-dom";
import jwt_decode from 'jwt-decode';
import header from '../header/Header.module.css'
import { logout } from "../../auth/logout";
import { useDispatch} from 'react-redux';
import { useNavigate } from 'react-router-dom';


const Header = () => {
    const navigate = useNavigate()
    // const dispatch = useDispatch()
 

    return (
        <header className={header.header}>
            <div className={header.main}>
                <div>
                    <NavLink to={"/"}>Main</NavLink>
                    {localStorage.getItem("access_token") !== null ?? <NavLink to={"/login"}>Login</NavLink>}
                    <p onClick={() => logout(navigate)}>Logout</p>
                </div>
                <div className={header.text}>
                    {jwt_decode(localStorage.getItem("access_token"))?.sub}
                </div>
            </div>
        </header>

    )
}
export default Header;