import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import style from "../CreatePage.module.css";

import {
  useGetBuildingByIdQuery,
  useGetLocationByIdQuery,
  useGetLocationsQuery,
  useUpdateBuildingMutation
} from "../../../../features/redux/api/structureApi";
import debounce from 'lodash.debounce';

const BuildingDetails = () => {

  const navigate = useNavigate();

  const {id} = useParams();

  const [buildingLocationId, setBuildingLocationId] = useState(Number);
  const [buildingName, setBuildingName] = useState('');
  const [filter, setFilter] = useState('');


  const {data: buildingsData, isLoading: isLoadingBuildings} = useGetBuildingByIdQuery(Number(id));
  const {data: locationData, isLoading} = useGetLocationByIdQuery(buildingsData?.location.id);

  const {data: pageableLocation} = useGetLocationsQuery({
    page: 0,
    sort: {
      element: "name",
      direction: "asc"
    },
    size: 10,
    filter: filter,
  });
  const [updateBuilding] = useUpdateBuildingMutation();


  useEffect(() => {
    if (buildingsData) {
      setBuildingName(buildingsData?.name)
      setBuildingLocationId(buildingsData.location.id)
    }
  }, [buildingsData]);

  if (isLoadingBuildings || isLoading) return <LoaderHook/>

  const handleUpdateBuilding = async (body, e) => {
    e.preventDefault()
    await updateBuilding(body).unwrap();
    navigate(`/dashboard/buildings`)
  }
  return (
    <>
      <div className={style.body}>
        <form action="submit">
          <div className={style.block}>
            <label htmlFor="location">{locationData?.name}</label><br/>
            <input name={filter} onChange={debounce((e) => setFilter(e.target.value), 500)} placeholder={"search"}/>
            <select value={buildingLocationId} onChange={(e) => setBuildingLocationId(e.target.value)}>
              <option key={buildingsData?.location}
                      value={buildingsData?.location}>id: {locationData?.id} name: {locationData?.name}</option>
              {pageableLocation?.content?.map(location => (
                <option key={location.id} value={location.id}>id: {location.id} name: {location.name}</option>
              ))}
            </select>{buildingLocationId}<br/>
            <label htmlFor="name">Name</label><br/>
            <input type="text" name="name" value={buildingName} onChange={(e) => setBuildingName(e.target.value)}/>
          </div>
          <button onClick={(e) => handleUpdateBuilding(
            {
              buildingId: Number(id),
              data: {
                name: buildingName,
                locationId: buildingLocationId
              }
            }, e)}>Update building
          </button>
        </form>
      </div>
    </>
  )
};

export default BuildingDetails;