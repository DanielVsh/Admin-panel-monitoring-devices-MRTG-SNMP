package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.repositories.BuildingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@Component
@RequiredArgsConstructor
public class BuildingImpl {
  private final BuildingRepo bRepo;

  public Collection<Building> getBuildings() {
    return bRepo.findAll();
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

  public Collection<Building> getBuildingsByIds(Collection<Long> ids) {
    return ids.stream()
      .map(this::getById)
      .collect(Collectors.toList());
  }


  public Building getById(Long id) {
    try {
      return bRepo.findById(id).orElseThrow(NotFoundException::new);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
