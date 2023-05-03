package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.entities.Location;
import lombok.RequiredArgsConstructor;
import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@PreAuthorize("hasAnyRole('ADMIN_VIEW')")
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
public class LogsController {
  private final Javers javers;

  @GetMapping()
  public ResponseEntity<?> getLogs(
    @PageableDefault(sort = "id", direction = DESC) Pageable pageable,
    @RequestParam(required = false) String filter
  ) {
    QueryBuilder jql = QueryBuilder.byClass(
      Location.class,
      Building.class,
      Device.class
    );

    var changes = javers.findChanges(jql.build());

    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), changes.size());

    var page = new PageImpl<>(changes.subList(start, end), pageable, changes.size());

    Map<String, Object> response = new HashMap<>();
    response.put("page", page);
    response.put("totalPages", page.getTotalPages());

    return ResponseEntity.ok(javers.getJsonConverter().toJson(response));
  }
}
