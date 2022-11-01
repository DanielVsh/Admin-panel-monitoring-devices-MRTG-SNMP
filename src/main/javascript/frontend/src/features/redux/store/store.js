import { configureStore} from "@reduxjs/toolkit";
import { setupListeners } from "@reduxjs/toolkit/dist/query";
import { structureApi } from './../api/structureApi';

export const store = configureStore({
    reducer: {
        [structureApi.reducerPath]: structureApi.reducer,
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware()
    .concat(structureApi.middleware),
});

setupListeners(store.dispatch);