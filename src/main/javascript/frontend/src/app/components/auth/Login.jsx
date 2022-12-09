import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../../../features/utils/axiosCustom";

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();
    const bodyFormData = new FormData();
    
    const sendLoginRequest = () => {
        bodyFormData.append("username", username);
        bodyFormData.append("password", password);
        axios.post("api/v1/auth/login", bodyFormData)
            .then((response) => {
                const access_token = response.data.access_token;
                const refresh_token = response.data.refresh_token;
                localStorage.setItem("access_token", "Bearer " +  access_token);
                localStorage.setItem("refresh_token", "Bearer " + refresh_token);
                navigate("/dashboard", { replace: true })
            }).catch(reason => {
                if (reason.status === 401) {
                    console.log("bad credentials")
                }
            })
    }
    return (
        <div>
            <input name={"username"} type={"text"} value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder={"Write your login"}
            />
            <input name={"password"} type={"password"} value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder={"Write your password"}
            />
            <button type={"submit"} onClick={() => sendLoginRequest()}>Login</button>
        </div>
    )
}

export default Login;