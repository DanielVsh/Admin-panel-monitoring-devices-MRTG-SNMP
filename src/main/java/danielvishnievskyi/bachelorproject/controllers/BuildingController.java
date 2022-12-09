package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.BuildingSpecification;
import danielvishnievskyi.bachelorproject.repositories.specifications.LocationSpecification;
import danielvishnievskyi.bachelorproject.services.BuildingService;
import danielvishnievskyi.bachelorproject.services.LocationService;
import danielvishnievskyi.bachelorproject.dto.BuildingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
@PreAuthorize("hasAnyRole('SUPER_ADMIN')")
@RequestMapping("api/v1/building")
@RequiredArgsConstructor
public class BuildingController {
  private final BuildingService buildingService;
  private final LocationService locationService;

  @GetMapping()
  @PreAuthorize("hasAnyRole('ADMIN')")
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
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Building> getBuildingById(@PathVariable Long id) {
    return ResponseEntity.ok(buildingService.getById(id).orElseThrow());
  }

  @PostMapping()
  public ResponseEntity<?> createBuilding(@RequestBody @Valid BuildingDto buildingDetails) {
    if (locationService.getById(buildingDetails.getLocationId()).isEmpty()) {
      return new ResponseEntity<>("Location id is null or location not found", BAD_REQUEST);
    }
    if (buildingService.getByName(buildingDetails.getName()).isPresent()) {
      return new ResponseEntity<>
        (format("Building with %s name already exists", buildingDetails.getName()), CONFLICT);
    }

    Building building = new Building(
      buildingDetails.getName(),
      locationService.getById(buildingDetails.getLocationId()).orElseThrow());
    buildingService.save(building);
    return ResponseEntity.ok(building);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateBuilding(@PathVariable Long id,
                                          @RequestBody @Valid BuildingDto buildingDetails) {
    if (buildingService.getById(id).isEmpty()) {
      return new ResponseEntity<>("Invalid id", BAD_REQUEST);
    }
    Building building = buildingService.getById(id).orElseThrow();
    building.setName(buildingDetails.getName());
    if (locationService.getById(buildingDetails.getLocationId()).isPresent()) {
      var location = locationService.getById(buildingDetails.getLocationId()).orElseThrow();
      building.setLocation(location);
    }
    buildingService.save(building);
    return ResponseEntity.ok(building);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Building> deleteBuilding(@PathVariable Long id) {
    return ResponseEntity.ok(buildingService.deleteById(id));
  }

  @DeleteMapping()
  public ResponseEntity<?> delete(@RequestParam Collection<Long> filter) throws IOException {
    return ResponseEntity.ok(buildingService.deleteManyByIds(filter));
  }
}
