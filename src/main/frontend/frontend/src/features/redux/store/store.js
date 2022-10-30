import {combineReducers, configureStore} from '@reduxjs/toolkit';
import userReducer from "../reducers/userSlice"
import thunkMiddleware from "redux-thunk"
import {persistReducer, persistStore} from "redux-persist"
import storage from "redux-persist/lib/storage"
import structureReducer from "../reducers/structureSlice"

const rootReducer = combineReducers({
    user: userReducer,
    locations: structureReducer,
});

const persistConfig = {
    key: "root",
    storage
};

const persistedReducer = persistReducer(persistConfig, rootReducer);

const store = configureStore({
    reducer: persistedReducer,
    middleware: [thunkMiddleware]
});

export const persistor = persistStore(store);
export default store;
