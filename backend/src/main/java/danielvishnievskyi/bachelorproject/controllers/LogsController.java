package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.entities.Location;
import lombok.RequiredArgsConstructor;
import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole('ADMIN_VIEW')")
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
public class LogsController {
  private final Javers javers;

  @GetMapping()
  public ResponseEntity<?> getLogs(@PageableDefault Pageable pageable) {
    QueryBuilder jql = QueryBuilder.byClass(
      Location.class,
      Building.class,
      Device.class,
      AdminProfile.class
    );

    var changes = javers.findChanges(jql.build());

    var page = new PageImpl<>(changes, pageable, changes.size());

    return ResponseEntity.ok(javers.getJsonConverter().toJson(page));
  }
}
