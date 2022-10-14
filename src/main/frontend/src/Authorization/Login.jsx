import React, {useState} from "react";
import axios from "../Utils/custom-axios";

const Login = () => {

    const [username, setUsername] = useState('admin');
    const [password, setPassword] = useState('admin');
    const [data, setData] = useState([]);

    const bodyFormData = new FormData();
    const sendLoginRequest = () => {
        bodyFormData.append("username", username);
        bodyFormData.append("password", password);
        axios.post("api/v1/auth/login", bodyFormData)
            .then((response) => {
                localStorage.setItem("access_token", "Bearer " + response.headers.get("access_token"));
                localStorage.setItem("refresh_token", "Bearer " + response.headers.get("refresh_token"));
            }).catch(reason => {
            if (reason.response.status === 401) {
                localStorage.setItem("access_token", null);
                localStorage.setItem("refresh_token", null);
            }
        })
    }

    const getInfo = () => {
        axios.get("api/v1/user", {}).then(res => setData(res.data));
    }
    const logout = () => {
        localStorage.setItem("access_token", null);
        localStorage.setItem("refresh_token", null);
    }

    return (
        <div>
            <input name={"username"} type={"text"} value={username} onChange={(e) => setUsername(e.target.value)}/>
            <input name={"password"} type={"password"} value={password} onChange={(e) => setPassword(e.target.value)}/>
            {username} {password}
            <button type={"submit"} onClick={() => sendLoginRequest()}>Login</button>
            <div>
                <div>
                    <button type={"submit"} onClick={() => getInfo()}>GetInfo</button>
                    <button type={"submit"} onClick={() => logout()}>Logout</button>
                    {data}
                </div>
            </div>
        </div>
    )
}

export default Login;