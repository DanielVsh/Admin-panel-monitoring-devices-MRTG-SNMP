package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.repositories.BuildingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BuildingImpl {
  private final BuildingRepo buildingRepo;

  public Collection<Building> findAll() {
    return buildingRepo.findAll();
  }

  public Page<Building> findAll(Pageable page) {
    return buildingRepo.findAll(page);
  }

  public Page<Building> findAll(Specification<Building> specification, Pageable page) {
    return buildingRepo.findAll(specification, page);
  }

  public void save(Building building) {
    buildingRepo.save(building);
  }

  public Building deleteById(Long id) {
    var building = getById(id).orElseThrow();
    buildingRepo.deleteById(id);
    return building;
  }

  public Optional<Building> getByName(String name) {
    return buildingRepo.getByName(name);
  }

  public Collection<Long> deleteAllById(Collection<Long> ids) {
    buildingRepo.deleteAllById(ids);
    return ids;
  }

  public Collection<Building> getManyByIds(Collection<Long> ids) {
    return ids.stream()
      .map(value -> getById(value).orElseThrow())
      .collect(Collectors.toList());
  }

  public Optional<Building> getById(Long id) {
      return buildingRepo.findById(id);
  }
}
