import React from "react";
import {Route, Routes, useLocation} from "react-router-dom";
import './App.css';
import Login from "./app/components/auth/Login";
import Foot from "./app/components/interface/foot/Foot";
import Header from "./app/components/interface/header/Header";
import NavigationBar from "./app/components/interface/navigationbar/NavigationBar";
import Buildings from "./app/components/pages/buildings/Buildings";
import Dashboard from "./app/components/pages/dashboard/Dashboard";
import Devices from "./app/components/pages/devices/Devices";
import BuildingDetails from "./app/components/pages/buildings/BuildingDetails";
import LocationDetails from './app/components/pages/locations/LocationDetails';
import Locations from "./app/components/pages/locations/Locations";
import Logs from './app/components/pages/logs/Logs';
import MainPage from "./app/components/pages/main/MainPage";
import MissingPage from "./app/components/pages/missingpage/MissingPage";
import AuthRouteView from "./features/hooks/authRoute/AuthRouteView";

import 'bootstrap/dist/css/bootstrap.min.css';
import {DeviceCreate} from "./app/components/pages/devices/DeviceCreate";
import {DeviceDetails} from "./app/components/pages/devices/DeviceDetails";
import {LocationCreate} from "./app/components/pages/locations/LocationCreate";
import {BuildingCreate} from "./app/components/pages/buildings/BuildingCreate";

function App() {

  const location = useLocation();

  const appContent = localStorage.getItem("access_token") === null
    ? (
      <div className="unLogged">
        {location.pathname !== "/login" && <Foot/>}
        <div className={"content"}>
          <Routes>
            <Route path={""} element={<MainPage/>}/>
            <Route path={"/login"} element={<Login/>}/>
            <Route path={"*"} element={<MissingPage/>}/>
          </Routes>
        </div>
      </div>
    )
    : (
      <>
        <div className="app-wrapper">
          <Header/>
          <NavigationBar/>
          <Foot/>
          <div className={"content"}>
            <Routes>
              <Route path={""} element={<MainPage/>}/>
              {/* <Route path={"login"} element={<Login />} /> */}
              <Route path={"dashboard"} element={<AuthRouteView/>}>
                <Route index element={<Dashboard/>}/>
                <Route path={"buildings"}>
                  <Route index element={<Buildings/>}/>
                  <Route path={"building/:id"} element={<BuildingDetails/>}/>
                  <Route path={"create"} element={<BuildingCreate/>}/>
                </Route>
                <Route path={"locations"}>
                  <Route index element={<Locations/>}/>
                  <Route path={"location/:id"} element={<LocationDetails/>}/>
                  <Route path={"create"} element={<LocationCreate/>}/>
                </Route>
                <Route path={"devices"}>
                  <Route index element={<Devices/>}/>
                  <Route path={"device/:id"} element={<DeviceDetails/>}/>
                  <Route path={"create"} element={<DeviceCreate/>}/>
                </Route>
                <Route path={"logs"} element={<Logs/>}/>
              </Route>
              <Route path={"*"} element={<MissingPage/>}/>
            </Routes>
          </div>
        </div>
      </>
    )


  return appContent;
}

export default App;
