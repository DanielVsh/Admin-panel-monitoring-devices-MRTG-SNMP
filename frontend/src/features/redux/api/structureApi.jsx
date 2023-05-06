import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { Mutex } from 'async-mutex';
import axios from 'axios';
import { currentIp } from "../../../settings";
import { logout } from './../../../app/components/auth/logout';

const mutex = new Mutex()
const baseQuery = fetchBaseQuery({
  baseUrl: `${currentIp}api/v1/`,
  prepareHeaders: (headers) => {
    if (localStorage.getItem("access_token")) {
      headers.set('Authorization', localStorage.getItem("access_token"));
    }
    return headers;
  },
})
const baseQueryWithReauth = async (args, api, extraOptions) => {
  await mutex.waitForUnlock()
  let result = await baseQuery(args, api, extraOptions)
  if (result.error && result.error.status === 401) {
    if (!mutex.isLocked()) {
      const release = await mutex.acquire()
      try {
       await axios.get(`${currentIp}api/v1/auth/token/refresh`, {
          headers: {
            "Authorization": localStorage.getItem("refresh_token")
          }
        }).then((response) => {
          localStorage.setItem("access_token", `Bearer ${response.data.access_token}`)
          result = baseQuery(args, api, extraOptions)
        }).catch(reason => {
          logout(null, null)
          window.location.href = "/login";
          alert("Your token has expired");
        })    
      } finally {
        release()
      }
    } else {
      await mutex.waitForUnlock()
      result = await baseQuery(args, api, extraOptions)
    }
  }
  return result
}

export const structureApi = createApi({
  reducerPath: "structureApi",
  baseQuery: baseQueryWithReauth,
  tagTypes: ["Location", "Building", "Device"],
  endpoints: (builder) => ({
    ////////////////////////////
    getLocations: builder.query({
      query: (pageable) => pageable
        ? `location?sort=${pageable.sort.element},${pageable.sort.direction}&size=${pageable.size}&page=${pageable.page}&filter=${pageable.filter}`
        : `location`,
      providesTags: ["Location", "Building", "Device"]
    }),

    getLocationById: builder.query({
      query: (id) => `location/${id}`,
      providesTags: ["Location", "Building", "Device"]
    }),

    createNewLocation: builder.mutation({
      query: (body) => ({
        url: "location",
        method: "POST",
        body: body
      }),
      invalidatesTags: ["Building", "Location", "Device"]
    }),

    updateLocation: builder.mutation({
      query: (body) => ({
        url: `location/${body.locationId}`,
        method: "PUT",
        body: body.data
      }),
      invalidatesTags: ["Building", "Location", "Device"]
    }),

    deleteLocation: builder.mutation({
      query: (id) => ({
        url: `location/${id}`,
        method: "DELETE",
      }),
      invalidatesTags: ["Location", "Building", "Device"]
    }),
    ////////////////////////////
    getBuilding: builder.query({
      query: (pageable) => pageable
        ? `building?sort=${pageable?.sort?.element},${pageable?.sort?.direction}&size=${pageable?.size}&page=${pageable?.page}&filter=${pageable?.filter}`
        : `building`,
      providesTags: ["Location", "Building", "Device"]
    }),

    getBuildingById: builder.query({
      query: (id) => `building/${id}`,
      providesTags: ["Building", "Location", "Device"]
    }),

    createNewBuilding: builder.mutation({
      query: (body) => ({
        url: "building",
        method: "POST",
        body: body
      }),
      invalidatesTags: ["Building", "Location", "Device"]
    }),

    updateBuilding: builder.mutation({
      query: (body) => ({
        url: `building/${body.buildingId}`,
        method: "PUT",
        body: body.data
      }),
      invalidatesTags: ["Building", "Location", "Device"]
    }),

    deleteBuilding: builder.mutation({
      query: (id) => ({
        url: `building/${id}`,
        method: "DELETE",
      }),
      invalidatesTags: ["Building", "Location", "Device"]
    }),
    ////////////////////////////
    getDevice: builder.query({
      query: (pageable) => pageable
        ? `device?sort=${pageable?.sort?.element},${pageable?.sort?.direction}&size=${pageable?.size}&page=${pageable?.page}&filter=${pageable?.filter}`
        : `device`,
      providesTags: ["Location", "Building", "Device"]
    }),

    createNewDevice: builder.mutation({
      query: (body) => ({
        url: "device",
        method: "POST",
        body: body
      }),
      invalidatesTags: ["Building", "Location", "Device"]
    }),

    deleteDevice: builder.mutation({
      query: (id) => ({
        url: `device/${id}`,
        method: "DELETE",
      }),
      invalidatesTags: ["Device", "Building", "Location"]
    }),
    ////////////////////////////
    getAuditLogs: builder.query({
      query: (pageable) => pageable
        ? `logs?sort=${pageable?.sort?.element},${pageable?.sort?.direction}&size=${pageable?.size}&page=${pageable?.page}&timeFrom=${pageable?.time?.timeFrom}&timeTo=${pageable?.time?.timeTo}`
        : `logs`,
      providesTags: ["Location", "Building", "Device"]
    }),
    ////////////////////////////
  }),
});

export const {
  useGetLocationsQuery,
  useGetLocationByIdQuery,
  useCreateNewLocationMutation,
  useUpdateLocationMutation,
  useDeleteLocationMutation,
  ////////////////////////////
  useGetBuildingQuery,
  useGetBuildingByIdQuery,
  useCreateNewBuildingMutation,
  useUpdateBuildingMutation,
  useDeleteBuildingMutation,
  ////////////////////////////
  useGetDeviceQuery,
  useCreateNewDeviceMutation,
  useDeleteDeviceMutation,
  ////////////////////////////
  useGetAuditLogsQuery,
  useGetLocationAuditLogsQuery,
  useGetBuildingAuditLogsQuery,
  ////////////////////////////




} = structureApi;