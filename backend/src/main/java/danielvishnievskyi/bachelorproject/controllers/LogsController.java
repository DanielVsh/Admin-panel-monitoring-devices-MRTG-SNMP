package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.services.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@PreAuthorize("hasAnyRole('ADMIN_VIEW')")
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
public class LogsController {
  private final LogsService logsService;

  @GetMapping()
  public ResponseEntity<?> getLogs(
    @PageableDefault(sort = "id", direction = DESC) Pageable pageable,
    @RequestParam(required = false) String filter,
    @RequestParam(required = false) String timeFrom,
    @RequestParam(required = false) String timeTo
  ) {

    return ResponseEntity.ok(logsService.getLogs(
      pageable,
      filter,
      LocalDate.parse(timeFrom),
      LocalDate.parse(timeTo)
    ));
  }
}
