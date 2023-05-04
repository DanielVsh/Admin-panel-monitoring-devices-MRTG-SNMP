import JSOG from 'jsog';
import React, {useState} from "react";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import {useGetUserLocationsQuery} from "../../../../features/redux/api/structureApi";
import styles from './MainPage.module.css';
import ErrorPage from "../error/ErrorPage";

const MainPage = () => {

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);

  const {data: locationData, isLoading: locationIsLoading, error: locationIsError}
    = useGetUserLocationsQuery({
    page: page,
    sort: {
      element: "name",
      direction: "asc"
    },
    size: size,
    filter: ""
  })

  if (locationIsLoading) {
    return <LoaderHook/>
  }

  if (locationIsError) {
    return <ErrorPage/>
  }

  return (
    <>
      <img className={styles.uvtImg} src="UVT_TUKE.png" alt='UVT'/>
      <h2 className={styles.title}>MRTG TUKE UVT</h2>

      <h3 className={styles.subtitle}>Locations</h3>
      <div className={styles.block}>
        {locationData && JSOG.decode(locationData.content).map(location => (
          <div className={styles.content} key={location.id}>
            <h3>{location.name}</h3>
            {location.buildings.map(building => (
              <ul key={building.id}>
                <li
                  className={building?.devices?.length > 0 ? styles.buildingName : ''}
                  onClick={(event) => {
                    const devicesList = event.currentTarget.nextSibling;
                    devicesList.style.display = devicesList.style.display === 'none' ? 'block' : 'none';
                  }}
                >
                  <h4>{building.name}</h4>
                </li>
                <ul className={styles.devicesList} style={{display: 'none'}}>
                  {building.devices.map(device => {
                    const deviceTime = new Date(device?.uptime);
                    const currentTime = new Date();
                    const timeDiff = Math.abs(currentTime - deviceTime);
                    const days = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
                    const hours = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)).toString().padStart(2, '0');
                    const minutes = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60)).toString().padStart(2, '0');
                    const seconds = Math.floor((timeDiff % (1000 * 60)) / 1000).toString().padStart(2, '0');

                    return (
                      <li className={styles.text} key={device.id}>
                        <a className={styles.link} href={`/mrtg/${device.ipAddress}.html`}>{device?.name}</a>
                        {/* <Link className={styles.link} to={`/mrtg/${device.ipAddress}.html`}>{device.name}</Link> */}
                        &nbsp;&nbsp;{`uptime: ${days} days ${hours}:${minutes}:${seconds} hours`}
                      </li>
                    )
                  })}
                </ul>
              </ul>
            ))}
          </div>
        ))}
      </div>
      <div style={{
        display: "flex",
        justifyContent: "flex-end",
        paddingRight: "8%",
        paddingTop: "10px",
        alignItems: "center"
      }}>
        <select style={{borderStyle: "none", outline: "none"}} value={size} onChange={(e) => setSize(e.target.value)}>
          <option value={10}>{10} </option>
          <option value={20}>{20} </option>
          <option value={30}>{30} </option>
          <option value={40}>{40} </option>
        </select>
        <i className="bi bi-chevron-double-left" onClick={() => setPage(0)}></i>
        <i className="bi bi-chevron-left" onClick={() => setPage(page > 0 ? page - 1 : page)}></i>
        <i className="bi bi-chevron-right"
           onClick={() => setPage(locationData.totalPages - 1 > page ? page + 1 : page)}></i>
        <i className="bi bi-chevron-double-right" onClick={() => setPage(locationData.totalPages - 1)}></i>
        <span style={{fontSize: "14px", width: "100px", paddingBottom: "2px"}}>
          Page {page + 1} of {locationData.totalPages}
        </span>
      </div>
    </>
  )
}

export default MainPage;