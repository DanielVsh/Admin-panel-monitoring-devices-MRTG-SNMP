import React from "react";
import {Route, Routes} from "react-router-dom";
import './App.css';
import NavigationBar from "./app/components/interface/navigationbar/NavigationBar";
import Header from "./app/components/interface/header/Header";
import Foot from "./app/components/interface/foot/Foot";
import MainPage from "./app/components/pages/main/MainPage";
import Login from "./app/components/auth/Login";
import Logout from "./app/components/auth/Logout";
import AuthRoute from "./features/hooks/authRoute/AuthRoute";
import Dashboard from "./app/components/pages/dashboard/Dashboard";
import Buildings from "./app/components/pages/buildings/Buildings";
import Locations from "./app/components/pages/locations/Locations";
import Devices from "./app/components/pages/devices/Devices";
import MissingPage from "./app/components/pages/missingpage/MissingPage";


//TODO: 1.сделать рендер приложения при изменениях.
// 2. сделать обновление акцес токена спомощью рефреш токена
// 3. расделить таблицы по 10 на страницу
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
