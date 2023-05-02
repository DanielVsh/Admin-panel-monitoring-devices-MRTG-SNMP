import React from "react";
import { NavLink } from "react-router-dom";
import header from '../header/Header.module.css';
import MenuModal from "./MenuModal";



const Header = () => {
  return (
    <header className={header.header}>
      <div className={header.main}>
        <div>
          <NavLink to={"/"}>Main</NavLink>
        </div>
        <div>
          {localStorage.getItem("access_token") !== null ?? <NavLink to={"/login"}>Login</NavLink>}
          {localStorage.getItem("access_token") !== null && (<MenuModal />)}
        </div>
      </div>
    </header>
  )
}
export default Header;
