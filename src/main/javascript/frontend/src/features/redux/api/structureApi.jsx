import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const structureApi = createApi({
  reducerPath: "structureApi",
  baseQuery: fetchBaseQuery({
    baseUrl: "http://127.0.0.1:4200/api/v1/",
    prepareHeaders: (headers) => {
      headers.set('Authorization', localStorage.getItem("access_token"));
      return headers;
    },
  }),
  tagTypes: ["Location", "Building", "Device"],
  endpoints: (builder) => ({
    getLocations: builder.query({
      query: () => "location",
      providesTags: ["Location", "Building", "Device"]
    }),

    createNewLocation: builder.mutation({
      query: (body) => ({
        url: "location/create",
        method: "POST",
        body: body
      }),
      invalidatesTags: ["Building", "Location", "Device"]
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

    getBuildingById: builder.query({
      query: (id) => `building/${id}`
    }),

    createNewBuilding: builder.mutation({
      query: (body) => ({
        url: "building/create",
        method: "POST",
        body: body
      }),
      invalidatesTags: ["Building", "Location", "Device"]
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

    createNewDevice: builder.mutation({
      query: (body) => ({
        url: "device/create",
        method: "POST",
        body: body
      }),
      invalidatesTags: ["Building", "Location", "Device"]
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
  useGetBuildingByIdQuery,
  useCreateNewDeviceMutation,
  useCreateNewLocationMutation,
  useCreateNewBuildingMutation,
  
} = structureApi;