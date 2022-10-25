package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.BuildingsLocation;
import danielvishnievskyi.bachelorproject.services.BuildingsLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
public class BuildingsLocationController {
  private final BuildingsLocationService buildLocService;

  @GetMapping()
  public ResponseEntity<Collection<BuildingsLocation>> getBuildingsLocations() {
    return ResponseEntity.ok().body(buildLocService.getBuildingsLocations());
  }

  @PostMapping("/create")
  public ResponseEntity<BuildingsLocation> createBuildingsLocation(@RequestBody BuildingsLocation buildingsLocationDetails) {
    if (buildingsLocationDetails.getName() == null) {
      return ResponseEntity.badRequest().body(buildingsLocationDetails);
    }
    buildLocService.save(buildingsLocationDetails);
    return ResponseEntity.ok().body(buildingsLocationDetails);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BuildingsLocation> updateBuildingsLocation(@PathVariable Long id,
                                                                   @RequestBody BuildingsLocation buildingsLocationDetails) {
    BuildingsLocation buildingsLocation = buildLocService.getById(id);
    buildingsLocation.setName(buildingsLocationDetails.getName());
    buildingsLocation.setBuildings(buildingsLocationDetails.getBuildings());
    buildLocService.save(buildingsLocation);
    return ResponseEntity.ok().body(buildingsLocation);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Building> delete(@PathVariable Long id) {
    buildLocService.delete(id);
    return ResponseEntity.ok().build();
  }

}


