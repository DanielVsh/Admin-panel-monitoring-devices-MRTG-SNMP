import ReactLoading from "react-loading";
import React from "react";
import loader from "./Loader.module.css"

const LoaderHook = () => {
    return (
        <div className={loader.loader}>
            <ReactLoading
                type="spinningBubbles"
                color="#04AA6DFF"
                width={'40%'}/>
        </div>
    )
}

export default LoaderHook;