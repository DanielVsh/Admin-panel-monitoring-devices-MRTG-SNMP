package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final LocationService locationService;

  @GetMapping
  public ResponseEntity<?> getLocations(
    @PageableDefault(sort = "id", direction = DESC) Pageable page,
    @RequestParam(required = false) String filter
  ) {
    return ResponseEntity.ok(locationService.findAll(page));
  }
}
