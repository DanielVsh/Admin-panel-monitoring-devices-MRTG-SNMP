import { structureApi } from "../../../features/redux/api/structureApi";

export function logout(navigate, dispatch) {
  const clearData = () => {
    localStorage.clear();
    dispatch && dispatch(structureApi.util.resetApiState());
  }
  clearData();
  navigate && navigate("/", { replace: true });
  window.location.reload();
}