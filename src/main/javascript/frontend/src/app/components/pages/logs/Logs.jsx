import LoaderHook from "../../../../features/hooks/loader/LoaderHook";
import { useGetLogsQuery } from "../../../../features/redux/api/structureApi";
import { useState } from 'react';
import JSOG from 'jsog';

const Logs = () => {
  const [id, setId] = useState();

  const { data: logs, isLoading } = useGetLogsQuery({
    page: 0,
    sort: {
      element: "id",
      direction: "desc"
    },
    size: 1000,
    filter: id,
  })

  if (isLoading) return <LoaderHook />


  console.log(logs)
  return (
    <>
      <input onChange={(e) => setId(e.target.value)}></input>
      {JSOG.decode(logs?.content)?.map(log => (
        <div>logid: {log?.metadata?.delegate?.id} name: {log?.entity?.name ?? "null"} username: {log?.metadata?.delegate?.username ?? "null"} action: {log?.metadata?.revisionType}</div>
      ))}
    </>
  )
}

export default Logs;