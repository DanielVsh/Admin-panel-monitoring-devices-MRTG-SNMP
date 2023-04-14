package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.repositories.BuildingRepo;
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
  private final BuildingRepo buildingRepo;

  public Page<Building> findAll(Pageable page) {
    return buildingRepo.findAll(page);
  }

  public Page<Building> findAll(Specification<Building> specification, Pageable page) {
    return buildingRepo.findAll(specification, page);
  }

  public Collection<Building> findAllById(Collection<Long> ids) {
    return buildingRepo.findAllById(ids);
  }

  public Optional<Building> findById(Long id) {
    return buildingRepo.findById(id);
  }

  public Optional<Building> findByName(String name) {
    return buildingRepo.findByName(name);
  }

  public void deleteById(Long id) {
    buildingRepo.deleteById(id);
  }

  public void deleteAllById(Collection<Long> ids) {
    buildingRepo.deleteAllById(ids);
  }

  public void save(Building building) {
    buildingRepo.save(building);
  }
}
