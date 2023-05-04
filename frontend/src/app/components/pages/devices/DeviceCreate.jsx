import debounce from "lodash.debounce";
import React, {useEffect, useState} from "react";
import {useCreateNewDeviceMutation, useGetBuildingQuery} from "../../../../features/redux/api/structureApi";
import style from "../CreatePage.module.css";
import {PageNavigation} from "../../../../features/hooks/PageNavigation";
import {useNavigate} from "react-router-dom";

export const DeviceCreate = () => {
  const [name, setName] = useState('');
  const [ipAddress, setIpAddress] = useState('');
  const [switchMap, setSwitchMap] = useState(Boolean);
  const [SNMP, setSNMP] = useState('public');
  const [building, setBuilding] = useState(1);

  const navigate = useNavigate()

  const [filter, setFilter] = useState("");

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);

  const [createDevice] = useCreateNewDeviceMutation();
  const handleCreateDevice = async (body) => {
    await createDevice(body).unwrap();
  }

  const {data: buildingsData, isLoading: isLoadingBuildings} = useGetBuildingQuery({
    page: page,
    sort: {
      element: "id",
      direction: "asc"
    },
    size: size,
    filter: filter,
  });

  useEffect(() => {
    if (buildingsData) {
      setPage(0);
    }
  }, [filter]);

  return (
    <>
      <div className={style.body}>
        <form>
          <div className={style.block}>
            <div>
              <p>Create Device Form</p>
              <div>
                <input onChange={(e) =>
                  setName(e.target.value)} placeholder={"Device name"}/>
              </div>
              <div>
                <input name={"ipAddress"} value={ipAddress} onChange={(e) =>
                  setIpAddress(e.target.value)} required placeholder={"Device ip"}/>
              </div>
              <span style={{fontSize: "14px", color: "grey"}}>switchMap:</span>
              <div style={{display:"flex", flexDirection: 'row', marginTop: "-4px"}}>
                <div onClick={() => setSwitchMap(true)} style={{paddingRight: "5px", paddingLeft: "30px", color: switchMap ? "#04943b" : "grey", fontWeight: "bold"}}>TRUE</div>
                /
                <div onClick={() => setSwitchMap(false)} style={{paddingLeft: "5px", color: !switchMap ? "#04943b" : "grey", fontWeight: "bold"}}>FALSE</div>
              </div>
              <div>
                <input name={"SNMP"} value={SNMP} onChange={(e) =>
                  setSNMP(e.target.value)} placeholder={"SNMP community"}/>
              </div>
            </div>
            <div className={style.search}>
              <ii className="bi bi-building">Building: {building?.name}</ii>
              <input name={filter} onChange={debounce((e) => setFilter(e.target.value), 500)} placeholder={"Search"}/>
              <PageNavigation setPage={setPage} page={page} pageable={buildingsData}/>
              <div className={style.searched}>
                {buildingsData?.content?.map(building => (
                  <div onClick={() => setBuilding(building)} key={building?.id}>
                    {building?.id} {building?.name}
                  </div>
                ))}
              </div>
            </div>
          </div>
          <button className="btn btn-success" onClick={(e) => handleCreateDevice({
            name: name,
            ipAddress: ipAddress,
            uptime: null,
            switchMap: switchMap,
            snmp: SNMP,
            buildingId: building?.id
          }, e.preventDefault(), navigate("/dashboard/devices", { replace: true }))}>Create new Device
          </button>
        </form>
      </div>
    </>
  )
}