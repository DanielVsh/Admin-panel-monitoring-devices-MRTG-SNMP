package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.dto.LocationDTO;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.exception.location.LocationBadRequestException;
import danielvishnievskyi.bachelorproject.exception.location.LocationNotFoundException;
import danielvishnievskyi.bachelorproject.repositories.LocationRepo;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.LocationSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

import static danielvishnievskyi.bachelorproject.enums.SearchOperation.EQUAL;
import static danielvishnievskyi.bachelorproject.enums.SearchOperation.MATCH;

@Service
@RequiredArgsConstructor
public class LocationService {
  private final LocationRepo locationRepo;

  public Page<Location> getLocations(Pageable pageable, String filter) {
    LocationSpecification lcFilter = new LocationSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      lcFilter.add(new SearchCriteria("id", filter, EQUAL));
    } else if (StringUtils.hasLength(filter)) {
      lcFilter.add(new SearchCriteria("name", filter, MATCH));
    }
    return locationRepo.findAll(lcFilter, pageable);
  }

  public Location getLocation(Long id) {
    return locationRepo.findById(id)
      .orElseThrow(() -> new LocationNotFoundException(String.format("Location[%d]: Not Found", id)));
  }

  public Location createLocation(LocationDTO locationDTO) {
    locationRepo.findByName(locationDTO.name())
      .ifPresent(location -> {
        throw new LocationBadRequestException(String.format("Location %s already exists", location.getName()));
      });
    Location location = new Location(locationDTO.name());
    return locationRepo.save(location);
  }

  public Location updateLocation(Long id, LocationDTO locationDTO) {
    Location location = getLocation(id);
    BeanUtils.copyProperties(locationDTO, location);
    return locationRepo.save(location);
  }

  public void deleteLocation(Long id) {
    locationRepo.deleteById(id);
  }

  public void deleteLocations(Set<Long> ids) {
    locationRepo.deleteAllById(ids);
  }

}
