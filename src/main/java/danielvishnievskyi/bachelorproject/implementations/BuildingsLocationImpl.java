package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.BuildingsLocation;
import danielvishnievskyi.bachelorproject.repositories.BuildingLocationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;

import static org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@Component
@RequiredArgsConstructor
public class BuildingsLocationImpl {
  private final BuildingLocationRepo blRepo;

  public Collection<BuildingsLocation> getBuildingsLocations() {
    return blRepo.findAll();
  }

  public BuildingsLocation getById(Long id) {
    try {
      return blRepo.findById(id).orElseThrow(NotFoundException::new);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void delete(Long id) {
    blRepo.delete(getById(id));
  }

  public void save(BuildingsLocation buildingLocation) {
    blRepo.save(buildingLocation);
  }

  public void joinBuildingsToLocation(Collection<Building> buildings, Long buildingLocationId) {
    var buildingLocation = getById(buildingLocationId);
    buildingLocation.getBuildings().addAll(buildings);
    buildingLocation.setBuildings(buildingLocation.getBuildings());
    save(buildingLocation);
  }

  @Transactional
  public BuildingsLocation createIfNotFound(String name) {
    return blRepo.getByName(name.toUpperCase()).orElseGet(() -> {
      BuildingsLocation buildingsLocation = new BuildingsLocation(name.toUpperCase());
      save(buildingsLocation);
      return buildingsLocation;
    });
  }
}
