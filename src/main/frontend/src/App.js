import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import './App.css';
import Login from "./Authorization/Login";

function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <Routes>
                    <Route path={"/login"} element={<Login/>}/>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
