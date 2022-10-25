import React from "react";
import {useNavigate} from "react-router-dom";

const Logout = () => {
    const navigate = useNavigate();

    const logout = () => {
        localStorage.clear();
        navigate("/", {replace: true})
    }
    return (
        <div>
            <h1>Welcome to logout page</h1>
            <button type={"submit"} onClick={() => logout()}>Logout</button>
        </div>
    )
}

export default Logout;