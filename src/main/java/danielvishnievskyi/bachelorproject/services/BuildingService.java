package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.implementations.BuildingImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BuildingService {
  private final BuildingImpl bImpl;

  public Collection<Building> getBuildings() {
    return bImpl.getBuildings();
  }

  public void joinDevicesToBuilding(Collection<Device> devices, Long buildingId) {
    bImpl.joinDevicesToBuilding(devices, buildingId);
  }

  public Building getById(Long id) {
    return bImpl.getById(id);
  }

  public Building createIfNotFound(String name) {
    return bImpl.createIfNotFound(name);
  }

  public void deleteFromLocation(Long id) {
    bImpl.deleteFromLocation(id);
  }
  public void delete(Long id) {
    bImpl.delete(id);
  }

  public void save(Building building) {
    bImpl.save(building);
  }
}
