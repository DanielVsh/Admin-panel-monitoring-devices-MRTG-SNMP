import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useGetLocationByIdQuery, useUpdateLocationMutation} from "../../../../features/redux/api/structureApi";
import LoaderHook from './../../../../features/hooks/loader/LoaderHook';
import style from "../CreatePage.module.css";

const LocationDetails = () => {

  const navigate = useNavigate();

  const {id} = useParams();

  const {data: locationData, isLoading} = useGetLocationByIdQuery(Number(id))
  const [updateLocation] = useUpdateLocationMutation();

  const [locationName, setLocationName] = useState('')

  useEffect(() => {
    if (locationData) {
      setLocationName(locationData?.name)
    }
  }, [locationData]);

  if (isLoading) return <LoaderHook/>

  const handleUpdateLocation = async (body, e) => {
    e.preventDefault()
    await updateLocation(body).unwrap();
    navigate('/dashboard/locations')
  }


  return (
    <>
      <div className={style.body}>
        <form action="submit">
          <label htmlFor="name">Name</label><br/>
          <input type="text" name="name" value={locationName} onChange={(e) => setLocationName(e.target.value)}/>
          <button onClick={(e) => handleUpdateLocation(
            {
              locationId: Number(id),
              data: {
                name: locationName,
              }
            }, e)}>Update Location
          </button>
        </form>
      </div>
    </>
  )
};

export default LocationDetails;