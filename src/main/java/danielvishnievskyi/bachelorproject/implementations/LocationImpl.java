package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.repositories.BuildingLocationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;

import static org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@Component
@RequiredArgsConstructor
public class LocationImpl {
  private final BuildingLocationRepo blRepo;

  public Collection<Location> getBuildingsLocations() {
    return blRepo.findAll();
  }

  public Location getById(Long id) {
    try {
      return blRepo.findById(id).orElseThrow(NotFoundException::new);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void delete(Long id) {
    blRepo.delete(getById(id));
  }

  public void save(Location buildingLocation) {
    blRepo.save(buildingLocation);
  }


  @Transactional
  public Location createIfNotFound(String name) {
    return blRepo.getByName(name.toUpperCase()).orElseGet(() -> {
      Location buildingsLocation = new Location(name.toUpperCase());
      save(buildingsLocation);
      return buildingsLocation;
    });
  }
}
