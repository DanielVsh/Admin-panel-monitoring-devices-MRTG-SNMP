package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.dto.BuildingDTO;
import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.services.BuildingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RestController
@RequestMapping("api/v1/building")
@RequiredArgsConstructor
public class BuildingController {
  private final BuildingService buildingService;

  @GetMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<?> getBuildings(
    @PageableDefault(sort = "id", direction = DESC) Pageable page,
    @RequestParam(required = false) String filter
  ) {
    return ResponseEntity.ok(buildingService.getBuildings(page, filter));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Building> getBuilding(@PathVariable Long id) {
    return ResponseEntity.ok(buildingService.getBuilding(id));
  }

  @PostMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<?> createBuilding(@RequestBody @Valid BuildingDTO buildingDetails) {
    return ResponseEntity.ok(buildingService.createBuilding(buildingDetails));
  }


  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_WRITE')")
  public ResponseEntity<?> updateBuilding(
    @PathVariable Long id,
    @RequestBody @Valid BuildingDTO buildingDetails
  ) {
    return ResponseEntity.ok(buildingService.updateBuilding(id, buildingDetails));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
    buildingService.deleteBuilding(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping()
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public ResponseEntity<Void> deleteBuildings(@RequestParam Set<Long> ids) {
    buildingService.deleteBuildings(ids);
    return ResponseEntity.ok().build();
  }
}
