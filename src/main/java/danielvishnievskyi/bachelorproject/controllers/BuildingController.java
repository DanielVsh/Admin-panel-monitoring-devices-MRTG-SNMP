package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.dto.BuildingDTO;
import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.BuildingSpecification;
import danielvishnievskyi.bachelorproject.services.BuildingService;
import danielvishnievskyi.bachelorproject.services.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

import static danielvishnievskyi.bachelorproject.enums.SearchOperation.EQUAL;
import static danielvishnievskyi.bachelorproject.enums.SearchOperation.MATCH;
import static java.lang.String.format;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@RestController
@RequestMapping("api/v1/building")
@RequiredArgsConstructor
public class BuildingController {
  private final BuildingService buildingService;
  private final LocationService locationService;

  @GetMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<?> getBuildings(
    @PageableDefault(sort = "id", direction = DESC) Pageable page,
    @RequestParam(required = false) String filter
  ) {
    BuildingSpecification buildFilter = new BuildingSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      buildFilter.add(new SearchCriteria("id", filter, EQUAL));
    } else if (StringUtils.hasLength(filter)) {
      buildFilter.add(new SearchCriteria("name", filter, MATCH));
    }

    return ResponseEntity.ok(buildingService.findAll(buildFilter, page));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Building> getBuildingById(@PathVariable Long id) {
    return ResponseEntity.ok(buildingService.findById(id).orElseThrow());
  }

  @PostMapping()
  @PreAuthorize("hasAnyRole('ADMIN_WRITE')")
  public ResponseEntity<?> createBuilding(@RequestBody @Valid BuildingDTO buildingDetails) {
    if (locationService.findById(buildingDetails.getLocationId()).isEmpty()) {
      return new ResponseEntity<>("Location id is null or location not found", BAD_REQUEST);
    }
    if (buildingService.findByName(buildingDetails.getName()).isPresent()) {
      return new ResponseEntity<>
        (format("Building with %s name already exists", buildingDetails.getName()), CONFLICT);
    }

    Building building = new Building(
      buildingDetails.getName(),
      locationService.findById(buildingDetails.getLocationId()).orElseThrow());
    buildingService.save(building);
    return ResponseEntity.ok(building);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_WRITE')")
  public ResponseEntity<?> updateBuilding(@PathVariable Long id,
                                          @RequestBody @Valid BuildingDTO buildingDetails) {
    if (buildingService.findById(id).isEmpty()) {
      return new ResponseEntity<>("Invalid id", BAD_REQUEST);
    }
    Building building = buildingService.findById(id).orElseThrow();
    building.setName(buildingDetails.getName());
    if (locationService.findById(buildingDetails.getLocationId()).isPresent()) {
      var location = locationService.findById(buildingDetails.getLocationId()).orElseThrow();
      building.setLocation(location);
    }
    buildingService.save(building);
    return ResponseEntity.ok(building);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public ResponseEntity<Building> deleteBuilding(@PathVariable Long id) {
    buildingService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping()
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public ResponseEntity<?> delete(@RequestParam Collection<Long> filter) throws IOException {
    buildingService.deleteAllById(filter);
    return ResponseEntity.ok().build();
  }
}
