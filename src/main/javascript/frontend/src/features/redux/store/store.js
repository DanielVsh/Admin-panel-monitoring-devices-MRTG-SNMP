import { configureStore} from "@reduxjs/toolkit";
import { setupListeners } from "@reduxjs/toolkit/dist/query";
import { structureApi } from './../api/structureApi';
import { authApi } from './../api/authApi';

export const store = configureStore({
    reducer: {
        [structureApi.reducerPath]: structureApi.reducer,
        [authApi.reducerPath]: authApi.reducer,
        
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware()
    .concat(
        structureApi.middleware,
         authApi.middleware
         ),
});

setupListeners(store.dispatch);