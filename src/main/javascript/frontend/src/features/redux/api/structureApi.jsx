import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const structureApi = createApi({
  reducerPath: "structureApi",
  baseQuery: fetchBaseQuery({
    baseUrl: "http://localhost:4200/api/v1/",
    prepareHeaders: (headers) => {
      //TODO: взять куки и запихнуть их в хедер
      headers.set('Authorization', localStorage.getItem("access_token"));
      // headers.set('XSRF-TOKEN', document.cookie);
      return headers;
    },
  }),
  tagTypes: ["Location", "Building", "Device"],
  endpoints: (builder) => ({
    getLocations: builder.query({
      query: () => "location",
      providesTags: ["Location", "Building", "Device"]
    }),

    deleteLocation: builder.mutation({
      query: (id) => ({
        url: `location/delete/${id}`,
        method: "DELETE",
      }),
      invalidatesTags: ["Location", "Building", "Device"]
    }),

    getBuilding: builder.query({
      query: () => "building",
      providesTags: ["Building", "Location", "Device"]
    }),

    deleteBuilding: builder.mutation({
      query: (id) => ({
        url: `building/delete/${id}`,
        method: "DELETE",
      }),
      invalidatesTags: ["Building", "Location", "Device"]
    }),

    getDevice: builder.query({
      query: () => "device",
      providesTags: ["Device", "Building", "Location"]
    }),

    deleteDevice: builder.mutation({
      query: (id) => ({
        url: `device/delete/${id}`,
        method: "DELETE",
      }),
      invalidatesTags: ["Device", "Building", "Location"]
    }),


  }),
});

export const {
  useGetLocationsQuery,
  useDeleteLocationMutation,
  useGetBuildingQuery,
  useDeleteBuildingMutation,
  useGetDeviceQuery,
  useDeleteDeviceMutation,
} = structureApi;