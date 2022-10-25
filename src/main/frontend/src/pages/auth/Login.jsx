import React, {useState} from "react";
import axios from "../../utils/CustomAxios";
import {useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";
import {setAdmin, setIsLogged} from "../../redux/reducers/AuthSlice";

const Login = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const bodyFormData = new FormData();

    const sendLoginRequest = () => {
        bodyFormData.append("username", username);
        bodyFormData.append("password", password);
        axios.post("api/v1/auth/login", bodyFormData)
            .then((response) => {
                localStorage.setItem("access_token", "Bearer " + response.data.access_token);
                localStorage.setItem("refresh_token", "Bearer " + response.data.refresh_token);
                dispatch(setIsLogged(true))
                axios.get("api/v1/admin").then(res => {
                    dispatch(setAdmin(res.data))
                });
                navigate("/dashboard", {replace: true})
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