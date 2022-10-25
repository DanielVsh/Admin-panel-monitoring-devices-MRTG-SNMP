import React from "react";
import {Route, Routes} from "react-router-dom";
import './App.css';
import Login from "./pages/auth/Login";
import MissingPage from "./pages/missingpage/MissingPage";
import Dashboard from "./pages/adminpanel/dashboard/Dashboard";
import NavigationBar from "./pages/interface/navigationbar/NavigationBar";
import Header from "./pages/interface/header/Header";
import Logout from "./pages/auth/Logout";
import Locations from "./pages/adminpanel/locations/Locations";
import Buildings from "./pages/adminpanel/buildings/Buildings";
import Devices from "./pages/adminpanel/devices/Devices";
import MainPage from "./pages/main/MainPage";
import AuthRoute from "./utils/AuthRoute";
import Foot from "./pages/interface/foot/Foot";

//TODO: 1.сделать рендер приложения при изменениях.
// 2. сделать лоад скрин когда страница загружается
// 3. сделать таблицы для домов локаций и девайсов
// 4. сделать обновление акцес токена спомощью рефреш токена
function App() {
    return (
        <div className="app-wrapper">
            <Header/>
            <NavigationBar/>
            <Foot/>
            <div className={"content"}>
                <Routes>
                    <Route path={""} element={<MainPage/>}/>
                    <Route path={"login"} element={<Login/>}/>
                    <Route path={"logout"} element={<Logout/>}/>
                    <Route path={"dashboard"} element={<AuthRoute/>}>
                        <Route index element={<Dashboard/>}/>
                        <Route path={"buildings"} element={<Buildings/>}/>
                        <Route path={"locations"} element={<Locations/>}/>
                        <Route path={"devices"} element={<Devices/>}/>
                    </Route>
                    <Route path={"*"} element={<MissingPage/>}/>
                </Routes>
            </div>
        </div>
    );
}

export default App;
