import React from "react";

export const PageNavigation = ({setPage, page, pageable}) => {
	return (
		<>
			<i className="bi bi-chevron-double-left" onClick={() => setPage(0)}></i>
			<i className="bi bi-chevron-left" onClick={() => setPage(page > 0 ? page - 1 : page)}></i>
			<i className="bi bi-chevron-right"
				 onClick={() => setPage(pageable?.totalPages - 1 > page ? page + 1 : page)}></i>
			<i className="bi bi-chevron-double-right" onClick={() => setPage(pageable?.totalPages - 1)}></i>
			<span>
         Page {page + 1} of {pageable?.totalPages === 0 ? 1 : pageable?.totalPages}
      </span>
		</>
	)
}