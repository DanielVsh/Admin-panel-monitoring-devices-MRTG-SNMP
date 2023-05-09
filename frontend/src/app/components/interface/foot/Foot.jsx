import React from "react";
import foot from "./Foot.module.css"

const Foot = () => {
    return (
        <footer className={foot.foot}>
          Copyright © {new Date().getFullYear()} UVT / Daniel Vishnievskyi.
        </footer>
    )
}

export default Foot;