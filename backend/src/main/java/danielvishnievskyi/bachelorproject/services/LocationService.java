package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.repositories.LocationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService{
  private final LocationRepo locationRepo;

  public Page<Location> findAll(Pageable page) {
    return locationRepo.findAll(page);
  }

  public Collection<Location> findAll() {
    return locationRepo.findAll();
  }

  public Page<Location> findAll(Specification<Location> specification, Pageable page) {
    return locationRepo.findAll(specification, page);
  }

  public Optional<Location> findById(Long id) {
    return locationRepo.findById(id);
  }

  public Optional<Location> findByName(String name) {
    return locationRepo.findByName(name);
  }

  public void deleteById(Long id) {
    locationRepo.deleteById(id);
  }

  public void deleteAllById(Collection<Long> ids) {
    locationRepo.deleteAllById(ids);
  }

  public void save(Location buildingLocation) {
    locationRepo.save(buildingLocation);
  }
}
