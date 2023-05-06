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

/**
 * Service class that handles operations related to locations.
 *
 * @author [Daniel Vishnievskyi].
 */
@Service
@RequiredArgsConstructor
public class LocationService {
  private final LocationRepo locationRepo;

  /**
   * Retrieves a paginated list of locations based on the given filter and pageable.
   *
   * @param pageable The pageable object used to paginate the list of locations.
   * @param filter   The filter string used to search for locations. Can be an ID or name.
   * @return A page object containing a list of locations.
   */
  public Page<Location> getLocations(Pageable pageable, String filter) {
    LocationSpecification lcFilter = new LocationSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      lcFilter.add(new SearchCriteria("id", filter, EQUAL));
    } else if (StringUtils.hasLength(filter)) {
      lcFilter.add(new SearchCriteria("name", filter, MATCH));
    }
    return locationRepo.findAll(lcFilter, pageable);
  }

  /**
   * Retrieves a location by ID.
   *
   * @param id The ID of the location to retrieve.
   * @return The location with the given ID.
   * @throws LocationNotFoundException If the location with the given ID is not found.
   */
  public Location getLocation(Long id) {
    return locationRepo.findById(id)
      .orElseThrow(() -> new LocationNotFoundException(String.format("Location[%d]: Not Found", id)));
  }

  /**
   * Creates a new location.
   *
   * @param locationDTO The DTO object containing the data for the new location.
   * @return The newly created location.
   * @throws LocationBadRequestException If a location with the same name already exists.
   */
  public Location createLocation(LocationDTO locationDTO) {
    locationRepo.findByName(locationDTO.name())
      .ifPresent(location -> {
        throw new LocationBadRequestException(String.format("Location %s already exists", location.getName()));
      });
    Location location = new Location(locationDTO.name());
    return locationRepo.save(location);
  }

  /**
   * Updates an existing location.
   *
   * @param id          The ID of the location to update.
   * @param locationDTO The DTO object containing the updated data for the location.
   * @return The updated location.
   * @throws LocationNotFoundException If the location with the given ID is not found.
   */
  public Location updateLocation(Long id, LocationDTO locationDTO) {
    Location location = getLocation(id);
    BeanUtils.copyProperties(locationDTO, location);
    return locationRepo.save(location);
  }

  /**
   * Deletes a location by ID.
   *
   * @param id The ID of the location to delete.
   */
  public void deleteLocation(Long id) {
    locationRepo.deleteById(id);
  }

  /**
   * Deletes multiple locations by their IDs.
   *
   * @param ids A set of IDs of the locations to delete.
   */
  public void deleteLocations(Set<Long> ids) {
    locationRepo.deleteAllById(ids);
  }
}
