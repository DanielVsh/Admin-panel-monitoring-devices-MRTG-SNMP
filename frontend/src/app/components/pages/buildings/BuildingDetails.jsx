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
import JSOG from "jsog";
import {PageNavigation} from "../../../../features/hooks/PageNavigation";

const BuildingDetails = () => {

  const navigate = useNavigate();

  const {id} = useParams();

  const [location, setLocation] = useState(Number);
  const [buildingName, setBuildingName] = useState();
  const [filter, setFilter] = useState('');
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);


  const {data: buildingsData, isLoading: isLoadingBuildings} = useGetBuildingByIdQuery(Number(id));
  const {data: locationData, isLoading} = useGetLocationByIdQuery(buildingsData?.location.id);
  const {data: pageableLocation} = useGetLocationsQuery({
    page: page,
    sort: {
      element: "name",
      direction: "asc"
    },
    size: size,
    filter: filter,
  });

  const [updateBuilding] = useUpdateBuildingMutation();

  useEffect(() => {
    if (locationData) {
      setPage(0);
    }
  }, [filter]);

  useEffect(() => {
    if (buildingsData) {
      setLocation(locationData)
      setBuildingName(buildingsData?.name)
    }
  }, [buildingsData, locationData]);

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
            <div>
              <p>Change Building Form</p>
              <label htmlFor="name">Name</label><br/>
              <input name="name" value={buildingName} onChange={(e) => setBuildingName(e.target.value)}/>
            </div>
            <div className={style.search}>
              <ii className="bi bi-buildings-fill">Location: {location?.name}</ii>
              <input name={filter} onChange={debounce((e) => setFilter(e.target.value), 500)} placeholder={"Search"}/>
              <PageNavigation setPage={setPage} page={page} pageable={pageableLocation} />
              <div className={style.searched}>
                {JSOG.decode(pageableLocation?.content)?.map(location => (
                  <div onClick={() => setLocation(location)}>id: {location.id} name: {location.name}</div>
                ))}
              </div>
            </div>
          </div>
          <button className={"btn btn-success"} onClick={(e) => handleUpdateBuilding(
            {
              buildingId: Number(id),
              data: {
                name: buildingName,
                locationId: location?.id
              }
            }, e)}>Update Building
          </button>
        </form>
      </div>
    </>
  )
};

export default BuildingDetails;