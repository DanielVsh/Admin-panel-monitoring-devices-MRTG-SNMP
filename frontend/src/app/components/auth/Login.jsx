import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../../../features/utils/axiosCustom";
import { currentIp } from '../../../settings';
import LoginStyle from "../auth/Login.module.css";



const Login = () => {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const [error, setError] = useState("");

	const navigate = useNavigate();
	const bodyFormData = new FormData();
	const handleSubmit = (event) => {
		event.preventDefault();
		bodyFormData.append("username", username);
		bodyFormData.append("password", password);
		axios.post(`${currentIp}api/v1/auth/login`, bodyFormData)
			.then((response) => {
				const access_token = response.data.access_token;
				const refresh_token = response.data.refresh_token;
				localStorage.setItem("access_token", "Bearer " + access_token);
				localStorage.setItem("refresh_token", "Bearer " + refresh_token);
				navigate("/dashboard", { replace: true })
				window.location.reload();
			}).catch(reason => {
				setError("Invalid username or password");
			})
	}

	return (
		<div className={LoginStyle.loginContainer}>
			<div className={LoginStyle.loginBox}>
				<form className={LoginStyle.formStyle} onSubmit={handleSubmit}>
					<div className={LoginStyle.formGroup}>
						<label htmlFor="username">Username</label>
						<input
							type="text"
							id="username"
							placeholder="Enter username"
							value={username}
							onChange={(event) => setUsername(event.target.value)}
						/>
					</div>

					<div className={LoginStyle.formGroup}>
						<label htmlFor="password">Password</label>
						<input
							type="password"
							id="password"
							placeholder="Enter password"
							value={password}
							onChange={(event) => setPassword(event.target.value)}
						/>
					</div>
					<button style={{backgroundColor: "#5f855b"}} type="submit">Log In</button>
				</form>
				{error && <div class="alert alert-warning">
					<strong>Warning!</strong> {error}.
					<button onClick={() => setError('')} type="button" class="btn-close" data-bs-dismiss="alert"></button>
				</div>}
			</div>
		</div>
	);
};


export default Login;