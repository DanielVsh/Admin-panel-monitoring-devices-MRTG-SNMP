package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.services.BuildingService;
import danielvishnievskyi.bachelorproject.services.DeviceBuildingLocationService;
import danielvishnievskyi.bachelorproject.services.LocationService;
import dto.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
public class LocationController {
  private final LocationService locationService;
  private final BuildingService buildingService;
  private final DeviceBuildingLocationService devBuildLocService;


  @GetMapping()
  public ResponseEntity<Collection<Location>> getLocations() {
    return ResponseEntity.ok().body(locationService.getBuildingsLocations());
  }

  @PostMapping("/create")
  public ResponseEntity<Location> createLocation(@RequestBody LocationDto locationDetails) {
    if (locationDetails.getName() == null) {
      return ResponseEntity.badRequest().build();
    }
    Location location = locationService.createIfNotFound(locationDetails.getName());
    locationService.save(location);
    location.setBuildings(buildingService.getBuildingsByIds(locationDetails.getBuildingsIds()));
    devBuildLocService.joinBuildingsToLocation(locationDetails.getBuildingsIds(), location.getId());
    return ResponseEntity.ok().body(location);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Location> updateLocation(@PathVariable Long id,
                                                 @RequestBody Location buildingsLocationDetails) {
    Location buildingsLocation = locationService.getById(id);
    buildingsLocation.setName(buildingsLocationDetails.getName());
    buildingsLocation.setBuildings(buildingsLocationDetails.getBuildings());
    locationService.save(buildingsLocation);
    return ResponseEntity.ok().body(buildingsLocation);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Building> delete(@PathVariable Long id) {
    locationService.delete(id);
    return ResponseEntity.ok().build();
  }

}
