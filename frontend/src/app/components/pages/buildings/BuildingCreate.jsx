import table from "../TableStyle.module.css";
import debounce from "lodash.debounce";
import React, {useEffect, useState} from "react";
import {useCreateNewBuildingMutation, useGetLocationsQuery} from "../../../../features/redux/api/structureApi";

export const BuildingCreate = () => {
  const [filter, setFilter] = useState("");
  const [name, setName] = useState('');
  const [location, setLocation] = useState(Number);

  const [createBuilding] = useCreateNewBuildingMutation();

  const { data: pageableLocation } = useGetLocationsQuery({
    page: 0,
    sort: {
      element: "name",
      direction: "asc"
    },
    size: 5,
    filter: filter,
  });

  useEffect(() => {
    if (pageableLocation) {
      setLocation(pageableLocation?.content[0])
    }
  }, [pageableLocation]);

  // useEffect(() => {
  //   if (locationId) {
  //     setFilter(locationId)
  //   }
  // }, [locationId]);


  const handleCreateBuilding = async (body) => {
    await createBuilding(body).unwrap();
  }

  return (
    <>
      <form>
        <p>Create form</p>
        <input name={"name"} onChange={(e) => setName(e.target.value)} placeholder={"Building name"}/>
        <div>
          <div>Location: {location?.name}</div>
          <input className={table.chosenValue} name={filter} onChange={debounce((e) => setFilter(e.target.value), 500)}
                 placeholder={"search"}/>
          {filter.length > 0 &&
            pageableLocation?.content?.map(location => (
              <div onClick={() => setLocation(location)} key={location.id}
                   value={location.id}>id: {location.id} name: {location.name}</div>
            ))}

          {/* <select onChange={(e) => setLocationId(e.target.value)}>
                {pageableLocation?.content?.map(location => (
                  <option key={location.id} value={location.id}>id: {location.id} name: {location.name}</option>
                ))}
              </select>{locationId}<br /> */}
        </div>
        <button onClick={(e) => {
          handleCreateBuilding({
            name: name,
            locationId: location?.id
          }, e.preventDefault())
        }}>Create new Building
        </button>
      </form>
    </>
  )
}