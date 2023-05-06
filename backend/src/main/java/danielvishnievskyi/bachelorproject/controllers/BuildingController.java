package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.dto.BuildingDTO;
import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.services.BuildingService;
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

/**
 * The BuildingController class is responsible for handling all incoming requests related to buildings and forwarding them to
 * the BuildingService for processing.
 *
 * @author [Daniel Vishnievskyi]
 */
@RestController
@RequestMapping("api/v1/building")
@RequiredArgsConstructor
public class BuildingController {

  /**
   * The buildingService instance used to perform operations related to Building entity.
   */
  private final BuildingService buildingService;

  /**
   * Returns a paginated list of buildings based on the given parameters.
   *
   * @param page   the pageable object containing pagination and sorting information
   * @param filter an optional parameter to filter the results
   * @return a response entity containing the paginated list of buildings
   */
  @GetMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Page<Building>> getBuildings(
    @PageableDefault(sort = "id", direction = DESC) Pageable page,
    @RequestParam(required = false) String filter
  ) {
    return ResponseEntity.ok(buildingService.getBuildings(page, filter));
  }

  /**
   * Returns a building with the given ID.
   *
   * @param id the ID of the building to retrieve
   * @return a response entity containing the retrieved building
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Building> getBuilding(@PathVariable Long id) {
    return ResponseEntity.ok(buildingService.getBuilding(id));
  }

  /**
   * Creates a new building with the given details.
   *
   * @param buildingDetails the details of the building to create
   * @return a response entity containing the created building
   */
  @PostMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Building> createBuilding(@RequestBody @Valid BuildingDTO buildingDetails) {
    return ResponseEntity.ok(buildingService.createBuilding(buildingDetails));
  }

  /**
   * Updates an existing building with the given details.
   *
   * @param id              the ID of the building to update
   * @param buildingDetails the updated details of the building
   * @return a response entity containing the updated building
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_WRITE')")
  public ResponseEntity<Building> updateBuilding(
    @PathVariable Long id,
    @RequestBody @Valid BuildingDTO buildingDetails
  ) {
    return ResponseEntity.ok(buildingService.updateBuilding(id, buildingDetails));
  }

  /**
   * Deletes an existing building with the given ID.
   *
   * @param id the ID of the building to delete
   * @return a response entity indicating a successful deletion
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
    buildingService.deleteBuilding(id);
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes multiple buildings with the given IDs.
   *
   * @param ids the IDs of the buildings to delete
   * @return a response entity indicating a successful deletion
   */
  @DeleteMapping()
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public ResponseEntity<Void> deleteBuildings(@RequestParam Set<Long> ids) {
    buildingService.deleteBuildings(ids);
    return ResponseEntity.ok().build();
  }
}
