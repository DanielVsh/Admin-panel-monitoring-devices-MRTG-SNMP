import React, {useState} from 'react'
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import {useGetAuditLogsQuery} from "../../../../features/redux/api/structureApi";
import table from "../TableStyle.module.css";
import ErrorPage from "../error/ErrorPage";
import DatePicker from "react-multi-date-picker";

const Logs = () => {
  const [id, setId] = useState();

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);

  const [value, setValue] = useState(new Date());

  const pageable = {
    page: page,
    sort: {
      element: "id",
      direction: "desc"
    },
    size: size,
    filter: id,
    time: {
      timeFrom: "",
      timeTo: ""
    }
  }

  const maxDaysInMonth = (year, month) => {
    return new Date(year, month + 1, 0).getDate();
  };

  if (Array.isArray(value) && value.length === 2 && value[0]?.unix === value[1]?.unix) {
    pageable.time.timeFrom = `${value[0]?.year}-${(value[0]?.month?.number).toString().padStart(2, '0')}-${Math.max(value[0]?.day - 1, 1).toString().padStart(2, '0')}`;
    pageable.time.timeTo = `${value[0]?.year}-${(value[0]?.month?.number).toString().padStart(2, '0')}-${Math.min(value[0]?.day + 1, maxDaysInMonth(value[0]?.year, value[0]?.month?.number - 1)).toString().padStart(2, '0')}`;
  } else if (Array.isArray(value) && value.length === 2) {
    pageable.time.timeFrom = `${value[0]?.year}-${(value[0]?.month?.number).toString().padStart(2, '0')}-${Math.max(value[0]?.day - 1, 1).toString().padStart(2, '0')}`;
    pageable.time.timeTo = `${value[1]?.year}-${(value[1]?.month?.number).toString().padStart(2, '0')}-${Math.min(value[1]?.day + 1, maxDaysInMonth(value[1]?.year, value[1]?.month?.number - 1)).toString().padStart(2, '0')}`;
  } else if (value instanceof Date) {
    const date = value;
    pageable.time.timeFrom = `${date?.getFullYear()?.toString().padStart(2, '0')}-${(date?.getMonth()+1)?.toString().padStart(2, '0')}-${Math.max(date?.getDate()-1, 1)?.toString().padStart(2, '0')}`;
    pageable.time.timeTo = `${date?.getFullYear()?.toString().padStart(2, '0')}-${(date?.getMonth()+1)?.toString().padStart(2, '0')}-${Math.min(date?.getDate()+1, maxDaysInMonth(date?.getFullYear(), date?.getMonth())).toString().padStart(2, '0')}`;
  } else {
    pageable.time.timeFrom = `${value[0]?.year}-${(value[0]?.month?.number).toString().padStart(2, '0')}-${Math.max(value[0]?.day - 1, 1).toString().padStart(2, '0')}`;
    pageable.time.timeTo = `${value[0]?.year}-${(value[0]?.month?.number).toString().padStart(2, '0')}-${Math.min(value[0]?.day + 1, maxDaysInMonth(value[0]?.year, value[0]?.month?.number - 1)).toString().padStart(2, '0')}`;
  }

  const {data: auditLogs, isLoading: isLoadingLogs, error: isError} = useGetAuditLogsQuery(pageable);

  if (isLoadingLogs) {
    return <LoaderHook/>
  }

  if(isError) {
    return <ErrorPage/>
  }


  return (<>
    <table className={table.table}>
      <thead className={table.head}>
      <tr>
        <td className={table.minSize}>Revision</td>
        <td className={table.minSize}>Entity</td>
        <td className={table.minSize}>Id</td>
        <td>Value</td>
        <td style={{width: "160px"}}>Date</td>
        <td className={table.minSize}>Action</td>
        <td className={table.minSize}>Admin</td>
      </tr>
      </thead>
      <tbody>
      {auditLogs && auditLogs?.page?.content?.map((logs, id) => (
        <tr key={id}>
          <td>{logs?.commitMetadata?.id}</td>
          <td>{logs?.globalId?.entity.split(".").pop()}</td>
          <td>{logs?.globalId?.cdoId ?? "null"}</td>
          {logs?.changeType === "NewObject" && <td>created</td>}
          {(logs?.changeType === "InitialValueChange" || logs?.changeType === "TerminalValueChange") &&
            <td>Property ({logs?.property}): from {logs?.left ?? "null"} to {logs?.right ?? "null"}</td>}
          {(logs?.changeType === "ReferenceChange" || logs?.changeType === "ValueChange") &&
            <td>Property ({logs?.property}):
              from {logs?.left?.cdoId ?? logs?.left ?? "null"} to {logs?.right?.cdoId ?? logs?.right ?? "null"}</td>}
          {logs?.changeType === "ListChange" &&
            <td style={{display: "flex"}}>
              Property ({logs?.property}): Changed&nbsp;
              <div>
                {logs?.elementChanges.map((element, id) => `[${element?.value?.cdoId}]`).join(", ")}
              </div>
            </td>}
          {logs?.changeType === "ObjectRemoved" && <td>removed</td>}
          <td>{
            (() => {
              const date = new Date(logs?.commitMetadata?.commitDate);
              const day = date.getDate().toString().padStart(2, '0');
              const month = (date.getMonth() + 1).toString().padStart(2, '0');
              const year = date.getFullYear().toString();
              const hours = date.getHours().toString().padStart(2, '0');
              const minutes = date.getMinutes().toString().padStart(2, '0');
              const seconds = date.getSeconds().toString().padStart(2, '0');
              return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;
            })()
          }</td>
          <td>{logs?.changeType ?? "null"}</td>
          <td>{logs?.commitMetadata?.author ?? "null"}</td>
        </tr>
      ))}
      </tbody>
    </table>
    <div className={table.pageable}>
      <select value={size} onChange={(e) => setSize(e.target.value)}>
        <option value={10}>{10} </option>
        <option value={20}>{20} </option>
        <option value={30}>{30} </option>
        <option value={40}>{40} </option>
      </select>
      <i className="bi bi-chevron-double-left" onClick={() => setPage(0)}></i>
      <i className="bi bi-chevron-left" onClick={() => setPage(page > 0 ? page - 1 : page)}></i>
      <i className="bi bi-chevron-right"
         onClick={() => setPage(auditLogs.totalPages - 1 > page ? page + 1 : page)}></i>
      <i className="bi bi-chevron-double-right" onClick={() => setPage(auditLogs.totalPages - 1)}></i>
      <span>
          Page {page + 1} of {auditLogs.totalPages}
      </span>
    </div>
    <DatePicker value={value} onChange={setValue} range={true}/>
  </>)
}

export default Logs;