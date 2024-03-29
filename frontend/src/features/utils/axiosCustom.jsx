import axios from 'axios';

const axiosInstance = axios.create();
axiosInstance.interceptors.request.use((config) => {
  config.headers['Authorization'] = localStorage.getItem('access_token');
  config.baseURL = "/";
  return config;
});

export default axiosInstance;