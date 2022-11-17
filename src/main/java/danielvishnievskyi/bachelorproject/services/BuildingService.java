package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.implementations.BuildingImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingService {
  private final BuildingImpl buildingImpl;

  public Page<Building> findAll(Pageable page) {
    return buildingImpl.findAll(page);
  }

  public Page<Building> findAll(Specification<Building> specification, Pageable page) {
    return buildingImpl.findAll(specification, page);
  }

  public Collection<Building> getManyByIds(Collection<Long> ids) {
    return buildingImpl.getManyByIds(ids);
  }

  public Optional<Building> getById(Long id) {
    return buildingImpl.getById(id);
  }

  public Optional<Building> getByName(String name) {
    return buildingImpl.getByName(name);
  }

  public Building deleteById(Long id) {
    return buildingImpl.deleteById(id);
  }

  public Collection<Long> deleteManyByIds(Collection<Long> ids) {
    return buildingImpl.deleteAllById(ids);
  }

  public void save(Building building) {
    buildingImpl.save(building);
  }
}
