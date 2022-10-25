package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.repositories.DeviceRepo;
import danielvishnievskyi.bachelorproject.services.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class DeviceImpl {
  private final DeviceRepo deviceRepo;
  private final BuildingService buildingService;

  public Collection<Device> getDevices() {
    return deviceRepo.findAll();
  }

  public Collection<Device> getByBuilding(Long buildingId) {
    return buildingService.getById(buildingId).getDevices();
  }

  public void save(Device device) {
    deviceRepo.save(device);
  }

  public void deleteFromBuilding(Long id) {
    try {
      buildingService.save(buildingService.getBuildings().stream()
        .filter(buildLoc -> buildLoc.getDevices().remove(getById(id)))
        .findFirst().orElseThrow(NotFoundException::new));
      delete(id);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void delete(Long id) {
    deviceRepo.delete(getById(id));
  }

  public Device getById(Long id) {
    try {
      return deviceRepo.findById(id).orElseThrow(NotFoundException::new);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public Device getByName(String name) {
    try {
      return deviceRepo.getByName(name).orElseThrow(NotFoundException::new);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
