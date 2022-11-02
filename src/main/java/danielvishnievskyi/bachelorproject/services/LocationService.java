package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.implementations.LocationImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class LocationService {
  private final LocationImpl blImpl;

  public Collection<Location> getBuildingsLocations() {
    return blImpl.getBuildingsLocations();
  }

  public Location getById(Long id) {
    return blImpl.getById(id);
  }

  public void delete(Long id) {
    blImpl.delete(id);
  }

  public void save(Location buildingLocation) {
    blImpl.save(buildingLocation);
  }

  public Location createIfNotFound(String name) {
    return blImpl.createIfNotFound(name);
  }
}
