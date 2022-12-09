import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const structureApi = createApi({
  reducerPath: "structureApi",
  baseQuery: fetchBaseQuery({
    baseUrl: "http://147.232.205.203:8080/api/v1/",
    prepareHeaders: (headers) => {
      headers.set('Authorization', localStorage.getItem("access_token"));
      return headers;
    },
  }),
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
    getLogs: builder.query({
      query: (pageable) => pageable 
      ? `logs/${pageable?.filter}?sort=${pageable?.sort?.element},${pageable?.sort?.direction}&size=${pageable?.size}&page=${pageable?.page}`
      : `logs/${pageable?.filter}`,
      providesTags: ["Location", "Building", "Device"]
    })
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
  useGetLogsQuery,

} = structureApi;