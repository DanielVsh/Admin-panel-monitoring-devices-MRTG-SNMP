package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.BuildingsLocation;
import danielvishnievskyi.bachelorproject.implementations.BuildingsLocationImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BuildingsLocationService {
  private final BuildingsLocationImpl blImpl;

  public Collection<BuildingsLocation> getBuildingsLocations() {
    return blImpl.getBuildingsLocations();
  }

  public BuildingsLocation getById(Long id) {
    return blImpl.getById(id);
  }

  public void delete(Long id) {
    blImpl.delete(id);
  }

  public void save(BuildingsLocation buildingLocation) {
    blImpl.save(buildingLocation);
  }

  public void joinBuildingsToLocation(Collection<Building> buildings, Long buildingLocationId) {
    blImpl.joinBuildingsToLocation(buildings, buildingLocationId);
  }

  public BuildingsLocation createIfNotFound(String name) {
    return blImpl.createIfNotFound(name);
  }
}
