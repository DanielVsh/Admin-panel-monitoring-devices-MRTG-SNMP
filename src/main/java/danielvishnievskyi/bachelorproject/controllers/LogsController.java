package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.repositories.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.history.RevisionSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@PreAuthorize("hasAnyRole('SUPER_ADMIN')")
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
public class LogsController {
  private final LocationRepo locationService;
  private final BuildingRepo buildingService;
  private final DeviceRepo deviceService;
  private final AdminProfileRepo adminService;
  private final PrivilegeRepo privilegeService;
  private final RoleRepo roleService;
  private final AuditReader auditReader;

  @GetMapping("/device/{id}")
  public ResponseEntity<?> getDeviceLogs(@PathVariable Long id,
                                   @PageableDefault(sort = "id", direction = DESC) Pageable page) {

    AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(Location.class, false, true);
    PageRequest pageRequest = PageRequest.of(
      page.getPageNumber(),
      page.getPageSize(),
      RevisionSort.desc()
    );
//    return ResponseEntity.ok(pagedListHolder.getPageList());
    return ResponseEntity.ok(deviceService.findRevisions(id, pageRequest));
  }
}
