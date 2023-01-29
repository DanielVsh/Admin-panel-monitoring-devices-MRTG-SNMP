package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.repositories.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

  @GetMapping()
  public ResponseEntity<?> getDeviceLogs(@PageableDefault Pageable pageable) {


    return ResponseEntity.ok().build();
  }
}
