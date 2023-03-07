package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.dto.LocationDTO;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.LocationSpecification;
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
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@RestController
@PreAuthorize("hasAnyRole('SUPER_ADMIN')")
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
public class LocationController {
  private final LocationService locationService;


  @GetMapping()
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<?> getFilteredAndPageableLocations(
    @PageableDefault(sort = "id", direction = DESC) Pageable page,
    @RequestParam(required = false) String filter
  ) {
    LocationSpecification lcFilter = new LocationSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      lcFilter.add(new SearchCriteria("id", filter, EQUAL));
    }  else if (StringUtils.hasLength(filter)) {
      lcFilter.add(new SearchCriteria("name", filter, MATCH));
    }
    return ResponseEntity.ok(locationService.findAll(lcFilter, page));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
    if (locationService.getById(id).isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(locationService.getById(id).orElseThrow());
  }

  @PostMapping()
  public ResponseEntity<?> createLocation(@RequestBody @Valid LocationDTO locationDetails) {
    if (locationService.getByName(locationDetails.getName()).isPresent()) {
      return new ResponseEntity<>
        (String.format("Location with %s name already exists", locationDetails.getName()), CONFLICT);
    }
    Location location = new Location(locationDetails.getName());
    locationService.save(location);
    return ResponseEntity.ok(location);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateLocation(@PathVariable Long id,
                                          @RequestBody @Valid LocationDTO locationDetails) {
    if (locationService.getById(id).isEmpty()) {
      return new ResponseEntity<>("Invalid id", BAD_REQUEST);
    }
    Location location = locationService.getById(id).orElseThrow();
    location.setName(locationDetails.getName());
    locationService.save(location);
    return ResponseEntity.ok(location);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Location> delete(@PathVariable Long id) {
    return ResponseEntity.ok(locationService.delete(id));
  }

  @DeleteMapping()
  public ResponseEntity<Collection<Long>> delete(@RequestParam Collection<Long> filter) throws IOException {
    return ResponseEntity.ok(locationService.deleteManyByIds(filter));
  }

}
