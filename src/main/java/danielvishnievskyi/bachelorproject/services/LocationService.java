package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.implementations.LocationImpl;
import danielvishnievskyi.bachelorproject.repositories.specifications.LocationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
  private final LocationImpl locationImpl;

  public Page<Location> findAll(Pageable page) {
    return locationImpl.findAll(page);
  }

  public Page<Location> findAll(Specification<Location> specification, Pageable page) {
    return locationImpl.findAll(specification, page);
  }

  public Optional<Location> getById(Long id) {
    return locationImpl.getById(id);
  }

  public Optional<Location> getByName(String name) {
    return locationImpl.getByName(name);
  }

  public Location delete(Long id) {
    return locationImpl.deleteById(id);
  }

  public Collection<Long> deleteManyByIds(Collection<Long> ids) {
    locationImpl.deleteManyByIds(ids);
    return ids;
  }

  public void save(Location buildingLocation) {
    locationImpl.save(buildingLocation);
  }
}
