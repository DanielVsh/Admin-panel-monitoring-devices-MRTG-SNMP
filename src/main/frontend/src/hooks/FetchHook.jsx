import axios from "../utils/CustomAxios";
import {useEffect, useState} from "react";

const FetchHook = (url) => {
    const [data, setData] = useState(null);

    const fetchData = async () => {
        return await axios.get(url)
    }

    useEffect(() => {
        fetchData().then(value => {
            setData(value.data)
        });
    }, []);

    return data;
}

export default FetchHook;

