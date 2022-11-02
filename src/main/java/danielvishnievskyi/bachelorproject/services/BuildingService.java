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

  public Collection<Building> getBuildingsByIds(Collection<Long> ids) {
    return bImpl.getBuildingsByIds(ids);
  }

  public Building getById(Long id) {
    return bImpl.getById(id);
  }

  public Building createIfNotFound(String name) {
    return bImpl.createIfNotFound(name);
  }

  public void delete(Long id) {
    bImpl.delete(id);
  }

  public void save(Building building) {
    bImpl.save(building);
  }
}
