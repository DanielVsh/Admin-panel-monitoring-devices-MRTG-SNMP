package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.dto.LocationDTO;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
public class LocationController {
  private final LocationService locationService;

  @GetMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Page<Location>> getLocations(
    @PageableDefault(sort = "id", direction = DESC) Pageable page,
    @RequestParam(required = false) String filter
  ) {
    return ResponseEntity.ok(locationService.getLocations(page, filter));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Location> getLocation(@PathVariable Long id) {
    return ResponseEntity.ok(locationService.getLocation(id));
  }

  @PostMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Location> createLocation(@RequestBody @Valid LocationDTO locationDetails) {
    return ResponseEntity.ok(locationService.createLocation(locationDetails));
  }


  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_WRITE')")
  public ResponseEntity<Location> updateLocation(
    @PathVariable Long id,
    @RequestBody @Valid LocationDTO locationDetails
  ) {
    return ResponseEntity.ok(locationService.updateLocation(id, locationDetails));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('SUPER_ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    locationService.deleteLocation(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping()
  @PreAuthorize("hasRole('SUPER_ADMIN')")
  public ResponseEntity<Void> delete(@RequestParam Set<Long> ids) {
    locationService.deleteLocations(ids);
    return ResponseEntity.ok().build();
  }
}
