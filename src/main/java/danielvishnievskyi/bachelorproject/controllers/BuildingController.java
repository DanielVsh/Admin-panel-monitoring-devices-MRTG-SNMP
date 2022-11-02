package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.services.BuildingService;
import danielvishnievskyi.bachelorproject.services.DeviceBuildingLocationService;
import danielvishnievskyi.bachelorproject.services.DeviceService;
import dto.BuildingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@Slf4j
@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("api/v1/building")
@RequiredArgsConstructor
public class BuildingController {
  private final BuildingService buildingService;
  private final DeviceBuildingLocationService devBuildLocService;
  private final DeviceService deviceService;

  @GetMapping()
  public ResponseEntity<Collection<Building>> getBuildings() {
    return ResponseEntity.ok().body(buildingService.getBuildings());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Building> getBuildingById(@PathVariable Long id) {
    return ResponseEntity.ok(buildingService.getById(id));
  }

  @PostMapping("/create")
  public ResponseEntity<Building> createBuilding(@RequestBody BuildingDto buildingDetails) {
    if (buildingDetails.getName() == null) {
      return ResponseEntity.badRequest().build();
    }
    Building building = buildingService.createIfNotFound(buildingDetails.getName());
    building.setDevices(deviceService.getDevicesByIds(buildingDetails.getDevicesIds()));
    buildingService.save(building);
    devBuildLocService.joinBuildingsToLocation(Set.of(building.getId()), buildingDetails.getLocationId());
    return ResponseEntity.ok().body(building);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Building> updateBuilding(@PathVariable Long id, @RequestBody BuildingDto buildingDetails) {
    Building building = buildingService.getById(id);
    building.setName(buildingDetails.getName());
    building.setDevices(deviceService.getDevicesByIds(buildingDetails.getDevicesIds()));
    buildingService.save(building);
    return ResponseEntity.ok().body(building);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Building> deleteBuilding(@PathVariable Long id) {
    devBuildLocService.deleteBuildingsFromLocation(Set.of(id));
    return ResponseEntity.ok().build();
  }
}
