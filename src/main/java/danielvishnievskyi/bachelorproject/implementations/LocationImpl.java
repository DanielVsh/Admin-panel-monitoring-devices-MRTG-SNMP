package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.repositories.LocationRepo;
import danielvishnievskyi.bachelorproject.repositories.specifications.LocationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LocationImpl {
  private final LocationRepo locationRepo;

  public List<Location> findAll() {
    return locationRepo.findAll();
  }

  public Page<Location> findAll(Pageable page) {
    return locationRepo.findAll(page);
  }

  public Page<Location> findAll(Specification<Location> specification, Pageable page) {
    return locationRepo.findAll(specification, page);
  }

  public Optional<Location> getById(Long id) {
      return locationRepo.findById(id);
  }

  public Optional<Location> getByName(String name) {
    return locationRepo.getByName(name);
  }

  public Location deleteById(Long id) {
    var deletedLocation = getById(id).orElseThrow();
    locationRepo.deleteById(id);
    return deletedLocation;
  }

  public Collection<Long> deleteManyByIds(Collection<Long> ids) {
    locationRepo.deleteAllById(ids);
    return ids;
  }

  public void save(Location buildingLocation) {
    locationRepo.save(buildingLocation);
  }
}
