import ReactLoading from "react-loading";
import React from "react";
import loader from "./Loader.module.css"

const LoaderHook = () => {
    return (
        <div className={loader.loader}>
            <ReactLoading
                type="spin"
                color="#04AA6DFF"
                width={"10%"}
                />
        </div>
    )
}

export default LoaderHook;