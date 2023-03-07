package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.repositories.BuildingRepo;
import danielvishnievskyi.bachelorproject.repositories.DeviceRepo;
import danielvishnievskyi.bachelorproject.repositories.LocationRepo;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.PropertySource;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasAnyRole('SUPER_ADMIN')")
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
public class LogsController {
  private final LocationRepo locationService;
  private final BuildingRepo buildingService;
  private final DeviceRepo deviceService;
  private final Javers javers;


  @GetMapping()
  public ResponseEntity<?> getLogs(@PageableDefault Pageable pageable) {
    QueryBuilder jql = QueryBuilder.byClass(
        Location.class,
        Building.class,
        Device.class
      );

    var changes = javers.findChanges(jql.build());

    var page = new PageImpl<>(changes, pageable, changes.size());

    return ResponseEntity.ok(javers.getJsonConverter().toJson(page));
//    return ResponseEntity.ok(javers.getJsonConverter().toJson(changes));
  }

}
