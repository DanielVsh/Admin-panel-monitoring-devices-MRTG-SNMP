import debounce from "lodash.debounce";
import React, {useEffect, useState} from "react";
import {useCreateNewBuildingMutation, useGetLocationsQuery} from "../../../../features/redux/api/structureApi";
import style from "../CreatePage.module.css"
import {PageNavigation} from "../../../../features/hooks/PageNavigation";
import {useNavigate} from "react-router-dom";

export const BuildingCreate = () => {
  const [filter, setFilter] = useState("");
  const [name, setName] = useState('');
  const [location, setLocation] = useState(Number);

  const navigate = useNavigate()

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);

  const [createBuilding] = useCreateNewBuildingMutation();

  const {data: pageableLocation} = useGetLocationsQuery({
    page: page,
    sort: {
      element: "id",
      direction: "asc"
    },
    size: size,
    filter: filter,
  });


  const handleCreateBuilding = async (body) => {
    await createBuilding(body).unwrap();
  }

  useEffect(() => {
    if (pageableLocation) {
      setPage(0);
    }
  }, [filter]);


  return (
    <>
      <div className={style.body}>
        <form>
          <div className={style.block}>
            <div >
              <p>Create Building Form</p>
              <input name={"name"} onChange={(e) => setName(e.target.value)} placeholder={"Building name"}/>
            </div>
            <div className={style.search}>
              <ii className="bi bi-buildings-fill">Location: {location?.name}</ii>
              <input name={filter}
                     onChange={debounce((e) => setFilter(e.target.value), 500)}
                     placeholder={"Search"}/>
              <PageNavigation setPage={setPage} page={page} pageable={pageableLocation}/>
              <div className={style.searched}>
                {pageableLocation?.content?.map(location => (
                  <div onClick={() => setLocation(location)} key={location.id}
                       value={location.id}>id: {location.id} name: {location.name}</div>
                ))}
              </div>
            </div>
          </div>
          <button className="btn btn-success" onClick={(e) => {
            handleCreateBuilding({
              name: name,
              locationId: location
            }, e.preventDefault(), navigate("/dashboard/buildings", { replace: true }))
          }}>Create new Building
          </button>
        </form>
      </div>
    </>
  )
}