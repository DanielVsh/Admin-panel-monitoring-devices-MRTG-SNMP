import React from 'react'
import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import { useGetAuditLogsQuery, useGetBuildingAuditLogsQuery, useGetLocationAuditLogsQuery } from "../../../../features/redux/api/structureApi";
import { useState } from 'react';
import table from "../TableStyle.module.css";
import JSOG from 'jsog';

const Logs = () => {
  const [id, setId] = useState();

  const pageable = {
    page: 0,
    sort: {
      element: "id",
      direction: "desc"
    },
    size: 100,
    filter: id,
  }


  const { data: auditLogs, isLoading: isLoadingLogs } = useGetAuditLogsQuery(pageable);

  if (isLoadingLogs) {
    return <LoaderHook />
  }

  return (
    <>
      <table className={table.table}>
        <thead className={table.head}>
          <tr>
            <td className={table.minSize}>Revision</td>
            <td className={table.minSize}>Entity</td>
            <td className={table.minSize}>Id</td>
            <td>Value</td>
            <td className={table.minSize}>Action</td>
            <td className={table.minSize}>Admin</td>
          </tr>
        </thead>
        <tbody>
          {auditLogs && auditLogs.content.map((logs, id) => (
            <tr key={id}>
              <td>{logs?.commitMetadata?.id}</td>
              <td>{logs?.globalId?.entity.split(".").pop()}</td>
              <td>{logs?.globalId?.cdoId ?? "null"}</td>
              {logs?.changeType === "NewObject" && <td>created</td>}
              {(logs?.changeType === "InitialValueChange" || logs?.changeType === "TerminalValueChange") &&
                <td>Property ({logs?.property}): from {logs?.left ?? "null"} to {logs?.right ?? "null"}</td>}
              {(logs?.changeType === "ReferenceChange" || logs?.changeType === "ValueChange") &&
                <td>Property ({logs?.property}): from {logs?.left?.cdoId ?? logs?.left ?? "null"} to {logs?.right?.cdoId ?? logs?.right ?? "null"}</td>}
              {logs?.changeType === "ListChange" &&
                <td>
                  Property ({logs?.property}): Changed
                  <div style={{ display: "flex" }}>
                    {logs?.elementChanges.map((element, id) => (
                      <div key={id}>[{element?.value?.cdoId}]</div>
                    ))}
                  </div>
                </td>}
              {logs?.changeType === "ObjectRemoved" && <td>removed</td>}

              <td>{logs?.changeType ?? "null"}</td>
              <td>{logs?.commitMetadata?.author ?? "null"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  )
}

export default Logs;