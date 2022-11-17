import React from "react";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import { useGetBuildingQuery, useGetDeviceQuery, useGetLocationsQuery } from "../../../../features/redux/api/structureApi";
import ErrorPage from "../error/ErrorPage";
import dashboard from "./Dashboard.module.css"

const Dashboard = () => {

  const { data: pageableLocation, isLoading: LocIsLoading, isError: LocIsError }
  = useGetLocationsQuery();
  const { data: pageableBuilding, isLoading: buildIsLoading, isError: buildIsError }
  = useGetBuildingQuery();
  const { data: pageableDevices, isLoading: deviceIsLoading, isError: deviceIsError }
  = useGetDeviceQuery();

  if (LocIsLoading || buildIsLoading || deviceIsLoading) return <LoaderHook/>
  if(LocIsError || buildIsError || deviceIsError) return <ErrorPage/>
  
  return (
    <div className={dashboard}>
      <header>
        <h1>Dashboard</h1>
      </header>
      <div className={dashboard.main}>
        <div className={dashboard.locations}>
          Locations
          <div>
            {pageableLocation?.totalElements}
          </div>
          <div className={dashboard.button}>
            <button type={"button"}>Button</button>
          </div>
        </div>
        <div className={dashboard.buildings}>
          Buildings
          <div>
            {pageableBuilding?.totalElements}
          </div>
          <div className={dashboard.button}>
            <button type={"button"}>Button</button>
          </div>
        </div>
        <div className={dashboard.devices}>
          Devices
          <div>
            {pageableDevices?.totalElements}
          </div>
          <div className={dashboard.button}>
            <button type={"button"}>Button</button>
          </div>
        </div>
        <div className={dashboard.connectedDevices}>
          Connected devices
          <div className={dashboard.button}>
            <button type={"button"}>Button</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Dashboard;