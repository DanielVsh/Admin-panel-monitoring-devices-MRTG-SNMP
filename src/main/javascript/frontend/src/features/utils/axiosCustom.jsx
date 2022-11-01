import axios from 'axios';

const axiosInstance = axios.create();
axiosInstance.defaults.withCredentials = true;
axiosInstance.interceptors.request.use((config) => {
  config.headers['Authorization'] = localStorage.getItem('access_token');
  config.baseURL = "http://localhost:4200/";
  return config;
});

export default axiosInstance;