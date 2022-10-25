import {createSlice} from '@reduxjs/toolkit'

export const AuthSlice = createSlice({
    name: "auth",
    initialState: {
    },
    reducers: {
        setAdmin: (state, action) => {
            state.admin = action.payload;
        },
        setIsLogged: (state, action) => {
            state.isLogged = action.payload;
        }
    }
})

export const {setAdmin, setIsLogged} = AuthSlice.actions
export default AuthSlice.reducer