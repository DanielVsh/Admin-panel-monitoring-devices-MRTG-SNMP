package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.dto.LocationDTO;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.services.location.LocationService;
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
 * The LocationController class is responsible for handling all incoming requests related to locations and forwarding them to
 * the LocationService for processing.
 *
 * @author [Daniel Vishnievskyi]
 */
@RestController
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
public class LocationController {

  /**
   * The locationService instance used to perform operations related to Location entity.
   */
  private final LocationService locationService;

  /**
   * Retrieves a paginated list of locations with optional filter criteria.
   *
   * @param page   the Pageable object representing the pagination parameters.
   * @param filter the optional filter criteria for location search.
   * @return the ResponseEntity object containing the paginated list of locations matching the search criteria.
   */
  @GetMapping()
  public ResponseEntity<Page<Location>> getLocations(
    @PageableDefault(sort = "id", direction = DESC) Pageable page,
    @RequestParam(required = false) String filter
  ) {
    return ResponseEntity.ok(locationService.getFilteredAndPageableList(page, filter));
  }

  /**
   * Retrieves a single location by its ID.
   *
   * @param id the ID of the location to retrieve.
   * @return the ResponseEntity object containing the retrieved location.
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Location> getLocation(@PathVariable Long id) {
    return ResponseEntity.ok(locationService.getEntityById(id));
  }

  /**
   * Creates a new location.
   *
   * @param locationDetails the LocationDTO object representing the location details to create.
   * @return the ResponseEntity object containing the newly created location.
   */
  @PostMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Location> createLocation(@RequestBody @Valid LocationDTO locationDetails) {
    return ResponseEntity.ok(locationService.createEntity(locationDetails));
  }

  /**
   * Updates an existing location.
   *
   * @param id              the ID of the location to update.
   * @param locationDetails the LocationDTO object representing the updated location details.
   * @return the ResponseEntity object containing the updated location.
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_WRITE')")
  public ResponseEntity<Location> updateLocation(
    @PathVariable Long id,
    @RequestBody @Valid LocationDTO locationDetails
  ) {
    return ResponseEntity.ok(locationService.updateEntity(id, locationDetails));
  }

  /**
   * Deletes a single location by its ID.
   *
   * @param id the ID of the location to delete.
   * @return the ResponseEntity object indicating the success of the operation.
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('SUPER_ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    locationService.deleteEntityById(id);
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes multiple locations by their IDs.
   *
   * @param ids the set of IDs of the locations to delete.
   * @return the ResponseEntity object indicating the success of the operation.
   */
  @DeleteMapping()
  @PreAuthorize("hasRole('SUPER_ADMIN')")
  public ResponseEntity<Void> delete(@RequestParam Set<Long> ids) {
    locationService.deleteEntitiesByIds(ids);
    return ResponseEntity.ok().build();
  }
}
