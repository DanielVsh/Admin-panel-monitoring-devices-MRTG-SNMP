package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.services.BuildingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("api/v1/building")
@RequiredArgsConstructor
public class BuildingController {
  private final BuildingService buildingService;

  @GetMapping()
  public ResponseEntity<Collection<Building>> getBuildings() {
    return ResponseEntity.ok().body(buildingService.getBuildings());
  }

  @PostMapping("/create")
  public ResponseEntity<Building> createBuilding(@RequestBody Building buildingDetails) {
    if (buildingDetails.getName() == null) {
      return ResponseEntity.badRequest().body(buildingDetails);
    }
    buildingService.save(buildingDetails);
    return ResponseEntity.ok().body(buildingDetails);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Building> updateBuilding(@PathVariable Long id, @RequestBody Building buildingDetails) {
    Building building = buildingService.getById(id);
    building.setName(buildingDetails.getName());
    building.setDevices(buildingDetails.getDevices());
    buildingService.save(building);
    return ResponseEntity.ok().body(building);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Building> deleteBuilding(@PathVariable Long id) {
    buildingService.deleteFromLocation(id);
    return ResponseEntity.ok().build();
  }
}
