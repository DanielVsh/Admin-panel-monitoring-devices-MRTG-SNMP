import React, {useState} from "react";
import {useCreateNewLocationMutation} from "../../../../features/redux/api/structureApi";
import style from "../CreatePage.module.css"
import {useNavigate} from "react-router-dom";

export const LocationCreate = () => {
  const [name, setName] = useState('');

  const [createLocation] = useCreateNewLocationMutation();

  const navigate = useNavigate()

  const handleCreateLocation = async (body) => {
    await createLocation(body).unwrap();
  }

  return (
    <>
      <div className={style.body}>
        <form>
          <p>Create Location Form</p>
          <div>
            <input name={name} onChange={(e) => setName(e.target.value)} placeholder={"Location name"}/>
          </div>
          <div>
            <button class="btn btn-success" onClick={(e) => {
              handleCreateLocation({name: name}, e.preventDefault(), navigate("/dashboard/locations", { replace: true }))
            }}>Create new Location
            </button>
          </div>
        </form>
      </div>
    </>
  )
}