import {createEntityAdapter, createSlice} from "@reduxjs/toolkit";

export const userAdapter = createEntityAdapter();
export const userSelectors = userAdapter.getSelectors(state => state.user);

const userSlice = createSlice({
    name: "user",
    initialState: userAdapter.getInitialState(),
    reducers: {
        setUser: userAdapter.setOne,
        removeAllUsers: userAdapter.removeAll
    },
});

export const {setUser, removeAllUsers} = userSlice.actions;

export default userSlice.reducer;