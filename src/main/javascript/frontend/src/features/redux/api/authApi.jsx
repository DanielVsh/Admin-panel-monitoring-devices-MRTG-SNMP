import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { currentIp } from '../../../settings';

export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: fetchBaseQuery({
    baseUrl: `${currentIp}api/v1/`,
    prepareHeaders: (headers) => {
      headers.set('Authorization', localStorage.getItem("access_token"));
      return headers;
    },
  }),
  tagTypes: ["Admin"],
  endpoints: (builder) => ({
    getAdmin: builder.query({
      query: () => "admin",
      providesTags: ["Admin"]
    }),
  }),
});


export const {
  useGetAdminQuery,
} = authApi;