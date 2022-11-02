import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: fetchBaseQuery({
    baseUrl: "http://127.0.0.1:4200/api/v1/",
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
    })
  }),
});


export const {useGetAdminQuery} = authApi;