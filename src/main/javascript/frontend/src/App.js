import React from "react";
import { Route, Routes } from "react-router-dom";
import './App.css';
import Login from "./app/components/auth/Login";
import Logout from "./app/components/auth/Logout";
import Foot from "./app/components/interface/foot/Foot";
import Header from "./app/components/interface/header/Header";
import NavigationBar from "./app/components/interface/navigationbar/NavigationBar";
import Buildings from "./app/components/pages/buildings/Buildings";
import Dashboard from "./app/components/pages/dashboard/Dashboard";
import Devices from "./app/components/pages/devices/Devices";
import BuildingDetails from "./app/components/pages/edit/BuildingDetails";
import LocationDetails from './app/components/pages/edit/LocationDetails';
import Locations from "./app/components/pages/locations/Locations";
import Logs from './app/components/pages/logs/Logs';
import MainPage from "./app/components/pages/main/MainPage";
import MissingPage from "./app/components/pages/missingpage/MissingPage";
import AuthRouteView from "./features/hooks/authRoute/AuthRouteView";


//TODO: DONE! 1.сделать рендер приложения при изменениях.
// 2. сделать обновление акцес токена спомощью рефреш токена
// DONE! 3. расделить таблицы по 10 на страницу
function App() {

  const appContent = localStorage.getItem("access_token") === null
    ? (
      <div className="unLogged">
        <Foot />
        <div className={"content"}>
          <Routes>
            <Route path={""} element={<MainPage />} />
            <Route path={"login"} element={<Login />} />
            <Route path={"logout"} element={<Logout />} />
            <Route path={"*"} element={<Login />} />
          </Routes>
        </div>
      </div>
    )
    : (
      <div className="app-wrapper">
        <Header />
        <NavigationBar />
        <Foot />
        <div className={"content"}>
          <Routes>
            <Route path={""} element={<MainPage />} />
            <Route path={"login"} element={<Login />} />
            <Route path={"logout"} element={<Logout />} />
            <Route path={"dashboard"} element={<AuthRouteView />}>
              <Route index element={<Dashboard />} />
              <Route path={"buildings"}>
                <Route index element={<Buildings />} />
                <Route path={"building/:id"} element={<BuildingDetails />} />
              </Route>
              <Route path={"locations"} >
                <Route index element={<Locations />} />
                <Route path={"location/:id"} element={<LocationDetails />} />
              </Route>
              <Route path={"devices"} element={<Devices />} />
              <Route path={"logs"} element={<Logs />} />
            </Route>
            <Route path={"*"} element={<MissingPage />} />
          </Routes>
        </div>
      </div>
    )


  return appContent;
}

export default App;
