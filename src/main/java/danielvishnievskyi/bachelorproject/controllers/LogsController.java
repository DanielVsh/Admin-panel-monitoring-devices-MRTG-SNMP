package danielvishnievskyi.bachelorproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import danielvishnievskyi.bachelorproject.repositories.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


  @GetMapping("/{id}")
  public ResponseEntity<?> getLogs(@PathVariable Long id,
                                   @PageableDefault(sort = "id", direction = DESC) Pageable page) {
    Pageable pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), RevisionSort.desc());
    return ResponseEntity.ok(deviceService.findRevisions(id, pageable));
  }
}
