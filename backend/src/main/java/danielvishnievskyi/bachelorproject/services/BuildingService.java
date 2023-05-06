package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.dto.BuildingDTO;
import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.exception.building.BuildingBadRequestException;
import danielvishnievskyi.bachelorproject.exception.building.BuildingNotFoundException;
import danielvishnievskyi.bachelorproject.repositories.BuildingRepo;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.BuildingSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

import static danielvishnievskyi.bachelorproject.enums.SearchOperation.EQUAL;
import static danielvishnievskyi.bachelorproject.enums.SearchOperation.MATCH;

/**
 * Service class for managing buildings.
 *
 * @author [Daniel Vishnievskyi].
 */
@Service
@RequiredArgsConstructor
public class BuildingService {
  private final BuildingRepo buildingRepo;
  private final LocationService locationService;

  /**
   * Retrieves a page of buildings based on the provided pagination and filtering parameters.
   *
   * @param pageable the pagination information
   * @param filter   the filter parameter
   * @return a page of buildings
   */
  public Page<Building> getBuildings(Pageable pageable, String filter) {
    BuildingSpecification buildFilter = new BuildingSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      buildFilter.add(new SearchCriteria("id", filter, EQUAL));
    } else if (StringUtils.hasLength(filter)) {
      buildFilter.add(new SearchCriteria("name", filter, MATCH));
    }
    return buildingRepo.findAll(buildFilter, pageable);
  }

  /**
   * Retrieves a building with the provided id.
   *
   * @param id the id of the building to retrieve
   * @return the building with the provided id
   * @throws BuildingNotFoundException if the building with the provided id does not exist
   */
  public Building getBuilding(Long id) {
    return buildingRepo.findById(id)
      .orElseThrow(() -> new BuildingNotFoundException(String.format("Building[%d]: Not Found", id)));
  }

  /**
   * Creates a new building with the provided information.
   *
   * @param buildingDTO the DTO containing the information for the new building
   * @return the newly created building
   * @throws BuildingBadRequestException if a building with the same name already exists
   */
  public Building createBuilding(BuildingDTO buildingDTO) {
    Location location = locationService.getLocation(buildingDTO.locationId());

    buildingRepo.findByName(buildingDTO.name()).ifPresent(building -> {
      throw new BuildingBadRequestException(String.format("Building %s already exists", building.getName()));
    });

    return buildingRepo.save(new Building(buildingDTO.name(), location));
  }

  /**
   * Updates an existing building with the provided information.
   *
   * @param id          the id of the building to update
   * @param buildingDTO the DTO containing the updated information for the building
   * @return the updated building
   * @throws BuildingNotFoundException if the building with the provided id does not exist
   */
  public Building updateBuilding(Long id, BuildingDTO buildingDTO) {
    Building building = getBuilding(id);

    building.setName(buildingDTO.name());
    building.setLocation(locationService.getLocation(buildingDTO.locationId()));

    return buildingRepo.save(building);
  }

  /**
   * Deletes a building with the provided id.
   *
   * @param id the id of the building to delete
   * @throws IllegalArgumentException in case the given id is null
   */
  public void deleteBuilding(Long id) {
    buildingRepo.deleteById(id);
  }

  /**
   * Deletes multiple buildings with the provided ids.
   *
   * @param ids the ids of the buildings to delete
   * @throws IllegalArgumentException in case the given ids or one of its elements is null.
   */
  public void deleteBuildings(Set<Long> ids) {
    buildingRepo.deleteAllById(ids);
  }
}
