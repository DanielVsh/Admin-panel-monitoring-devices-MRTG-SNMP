import React, {useState} from "react";
import {useCreateNewLocationMutation} from "../../../../features/redux/api/structureApi";

export const LocationCreate = () => {
  const [name, setName] = useState('');
  const [dropdownCreateLocation, setDropdownCreateLocation] = useState(false);

  const [createLocation] = useCreateNewLocationMutation();


  const handleCreateLocation = async (body) => {
    await createLocation(body).unwrap();
  }

  return (
    <>
      <form>
        <p>Create form</p>
        <div>
          <input name={name} onChange={(e) => setName(e.target.value)} placeholder={"Location name"}/>
        </div>
        <div>
          <button onClick={(e) => {
            handleCreateLocation({name: name}, e.preventDefault())
            setDropdownCreateLocation(false)
          }}>Create
          </button>
        </div>
      </form>
    </>
  )
}