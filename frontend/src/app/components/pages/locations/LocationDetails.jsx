import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useGetLocationByIdQuery, useUpdateLocationMutation} from "../../../../features/redux/api/structureApi";
import LoaderHook from './../../../../features/hooks/loader/LoaderHook';
import style from "../CreatePage.module.css";
import ErrorPage from "../error/ErrorPage";

const LocationDetails = () => {

  const navigate = useNavigate();

  const {id} = useParams();

  const {data: locationData, isLoading, isError} = useGetLocationByIdQuery(Number(id))
  const [updateLocation] = useUpdateLocationMutation();

  const [locationName, setLocationName] = useState('')

  useEffect(() => {
    if (locationData) {
      setLocationName(locationData?.name)
    }
  }, [locationData]);


  const handleUpdateLocation = async (body, e) => {
    e.preventDefault()
    await updateLocation(body).unwrap();
    navigate('/dashboard/locations')
  }

  if (isLoading) return <LoaderHook/>
  if (isError) return <ErrorPage/>

  return (
    <>
      <div className={style.body}>
        <form action="submit">
          <div className={style.block}>
            <div>
              <p>Change Location Form</p>
              <label htmlFor="name">Name</label><br/>
              <input name="name" value={locationName} onChange={(e) => setLocationName(e.target.value)}/>
            </div>
          </div>
          <button className={"btn btn-success"} onClick={(e) => handleUpdateLocation(
            {
              locationId: Number(id),
              data: {
                name: locationName,
              }
            }, e)}>Update Location
          </button>
        </form>
        {/*<div className={style.list}>*/}
        {/*  {locationData && locationData?.buildings?.map(building => (*/}
        {/*    <div className={style.listElement}>*/}
        {/*      {building.id} {building.name}*/}
        {/*    </div>*/}
        {/*  ))}*/}
        {/*</div>*/}
      </div>
    </>
  )
};

export default LocationDetails;