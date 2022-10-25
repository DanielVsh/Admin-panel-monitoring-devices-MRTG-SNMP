import {combineReducers, configureStore} from '@reduxjs/toolkit'
import storage from "redux-persist/lib/storage"
import {persistReducer, persistStore} from "redux-persist";
import thunk from 'redux-thunk';
import authReducer from "./reducers/AuthSlice";

const reducers = combineReducers({
    auth: authReducer
});

const config = {
    key: "root",
    storage
};
const reducer = persistReducer(config, reducers);

export const store = configureStore({
    reducer: reducer,
    middleware: [thunk]
});

const persistor = persistStore(store);

export {persistor};