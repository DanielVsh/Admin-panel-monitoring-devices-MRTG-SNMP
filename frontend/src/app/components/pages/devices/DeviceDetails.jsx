import {useNavigate, useParams} from "react-router-dom";
import style from "../CreatePage.module.css";
import debounce from "lodash.debounce";
import {PageNavigation} from "../../../../features/hooks/PageNavigation";
import JSOG from "jsog";
import React, {useEffect, useState} from "react";
import {
  useCreateNewDeviceMutation,
  useGetBuildingByIdQuery,
  useGetBuildingQuery, useGetDeviceByIdQuery, useUpdateDeviceMutation
} from "../../../../features/redux/api/structureApi";
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import ErrorPage from "../error/ErrorPage";

export const DeviceDetails = () => {

  const { id } = useParams();

  const [name, setName] = useState('');
  const [ipAddress, setIpAddress] = useState('');
  const [switchMap, setSwitchMap] = useState(Boolean);
  const [SNMP, setSNMP] = useState('public');
  const [building, setBuilding] = useState(1);

  const navigate = useNavigate()

  const [filter, setFilter] = useState("");

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);

  const [updateDevice] = useUpdateDeviceMutation();
  const handleUpdateDevice = async (body) => {
    await updateDevice(body).unwrap();
  }

  const {data: deviceData, isLoading: isLoadingDevice, isError: isErrorDevice} = useGetDeviceByIdQuery(Number(id));
  const {data: buildingData, isLoading: isLoadingBuilding, isError: isErrorBuilding} = useGetBuildingByIdQuery(deviceData?.building?.id);
  const {data: buildingsData, isLoading: isLoadingBuildings, isError: isErrorBuildings} = useGetBuildingQuery({
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


  useEffect(() => {
    if (deviceData) {
      setBuilding(buildingData)
      setName(deviceData?.name)
      setIpAddress(deviceData?.ipAddress)
      setSwitchMap(deviceData?.switchMap)
      setSNMP(deviceData?.snmp)
    }
  }, [deviceData, buildingData]);

  if (isLoadingBuildings || isLoadingDevice || isLoadingBuilding) {
    return <LoaderHook/>
  }
  if (isErrorDevice || isErrorBuilding || isErrorBuildings) {
    return <ErrorPage />
  }

  console.log(SNMP)
  // console.log({deviceData, buildingData, buildingsData})

  return (
    <>
      <div className={style.body}>
        <form>
          <div className={style.block}>
            <div>
              <p>Update Device Form</p>
              <div>
                <input value={name} onChange={(e) =>
                  setName(e.target.value)} placeholder={"Device name"}/>
              </div>
              <div>
                <input name={"ipAddress"} value={ipAddress} onChange={(e) =>
                  setIpAddress(e.target.value)} required placeholder={"Device ip"}/>
              </div>
              <span style={{fontSize: "14px", color: "grey"}}>switchMap:</span>
              <div style={{display:"flex", flexDirection: 'row', marginTop: "-4px"}}>
                <div onClick={() => setSwitchMap(true)} style={{paddingRight: "5px", paddingLeft: "30px", color: switchMap ? "#04943b" : "grey", fontWeight: "bold", cursor: "pointer"}}>TRUE</div>
                /
                <div onClick={() => setSwitchMap(false)} style={{paddingLeft: "5px", color: !switchMap ? "#04943b" : "grey", fontWeight: "bold", cursor: "pointer"}}>FALSE</div>
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
                {JSOG.decode(buildingsData?.content)?.map(building => (
                  <div onClick={() => setBuilding(building)} key={building?.id}>
                    {building?.id} {building?.name}
                  </div>
                ))}
              </div>
            </div>
          </div>
          <button className="btn btn-success" onClick={(e) => handleUpdateDevice({
            deviceIp: Number(id),
            data: {
              name: name,
              ipAddress: ipAddress,
              uptime: deviceData.uptime,
              switchMap: switchMap,
              SNMP: SNMP,
              buildingId: building?.id
            }
          }, e.preventDefault(), navigate("/dashboard/devices", { replace: true }))}>Update Device
          </button>
        </form>
      </div>
    </>
  )
}