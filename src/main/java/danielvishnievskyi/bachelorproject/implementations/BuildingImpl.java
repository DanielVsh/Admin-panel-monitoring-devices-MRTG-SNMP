package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.repositories.BuildingRepo;
import danielvishnievskyi.bachelorproject.services.BuildingsLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

import static org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@Component
@RequiredArgsConstructor
public class BuildingImpl {
  private final BuildingRepo bRepo;
  private final BuildingsLocationService buildLocService;

  public Collection<Building> getBuildings() {
    return bRepo.findAll();
  }

  public void joinDevicesToBuilding(Collection<Device> devices, Long buildingId) {
    var build = getById(buildingId);
    build.getDevices().addAll(devices);
    build.setDevices(build.getDevices());
    save(build);
  }

  public void save(Building building) {
    bRepo.save(building);
  }

  public void delete(Long id) {
    bRepo.delete(getById(id));
  }

  @Transactional
  public Building createIfNotFound(String name) {
    return bRepo.getByName(name.toUpperCase()).orElseGet(() -> {
      Building building = new Building(name.toUpperCase());
      save(building);
      return building;
    });
  }

  public void deleteFromLocation(Long id) {
    try {
      buildLocService.save(buildLocService.getBuildingsLocations().stream()
        .filter(buildLoc -> buildLoc.getBuildings().remove(getById(id)))
        .findFirst().orElseThrow(NotFoundException::new));
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public Building getById(Long id) {
    try {
      return bRepo.findById(id).orElseThrow(NotFoundException::new);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
