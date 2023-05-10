package danielvishnievskyi.bachelorproject.services.location;

import danielvishnievskyi.bachelorproject.dto.LocationDTO;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.exception.location.LocationBadRequestException;
import danielvishnievskyi.bachelorproject.exception.location.LocationNotFoundException;
import danielvishnievskyi.bachelorproject.repositories.LocationRepository;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.LocationSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

import static danielvishnievskyi.bachelorproject.enums.SearchOperation.EQUAL;
import static danielvishnievskyi.bachelorproject.enums.SearchOperation.MATCH;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
  private final LocationRepository locationRepository;

  @Override
  public Page<Location> getFilteredAndPageableList(Pageable pageable, String filter) {
    LocationSpecification lcFilter = new LocationSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      lcFilter.add(new SearchCriteria("id", filter, EQUAL));
    } else if (StringUtils.hasLength(filter)) {
      lcFilter.add(new SearchCriteria("name", filter, MATCH));
    }
    return locationRepository.findAll(lcFilter, pageable);
  }

  @Override
  public Location getEntityById(Long id) {
    return locationRepository.findById(id)
      .orElseThrow(() -> new LocationNotFoundException(String.format("Location[%d]: Not Found", id)));
  }

  @Override
  public Location createEntity(LocationDTO entityDTO) {
    locationRepository.findByName(entityDTO.name())
      .ifPresent(location -> {
        throw new LocationBadRequestException(String.format("Location %s already exists", location.getName()));
      });
    return locationRepository.save(new Location(entityDTO.name()));
  }

  @Override
  public Location updateEntity(Long id, LocationDTO entityDTO) {
    Location location = getEntityById(id);
    location.setName(entityDTO.name());
    return locationRepository.save(location);
  }

  @Override
  public void deleteEntityById(Long id) {
    locationRepository.deleteById(id);
  }

  @Override
  public void deleteEntitiesByIds(Set<Long> ids) {
    locationRepository.deleteAllById(ids);
  }
}
