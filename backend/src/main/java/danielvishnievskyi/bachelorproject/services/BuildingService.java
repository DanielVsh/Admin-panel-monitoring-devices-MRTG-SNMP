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

@Service
@RequiredArgsConstructor
public class BuildingService {
  private final BuildingRepo buildingRepo;
  private final LocationService locationService;

  public Page<Building> getBuildings(Pageable pageable, String filter) {
    BuildingSpecification buildFilter = new BuildingSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      buildFilter.add(new SearchCriteria("id", filter, EQUAL));
    } else if (StringUtils.hasLength(filter)) {
      buildFilter.add(new SearchCriteria("name", filter, MATCH));
    }
    return buildingRepo.findAll(buildFilter, pageable);
  }

  public Building getBuilding(Long id) {
    return buildingRepo.findById(id)
      .orElseThrow(() -> new BuildingNotFoundException(String.format("Building[%d]: Not Found", id)));
  }

  public Building createBuilding(BuildingDTO buildingDTO) {
    Location location = locationService.getLocation(buildingDTO.locationId());

    buildingRepo.findByName(buildingDTO.name()).ifPresent(building -> {
      throw new BuildingBadRequestException(String.format("Building %s already exists", building.getName()));
    });

    return buildingRepo.save(
      new Building(
        buildingDTO.name(),
        location
      ));
  }

  public Building updateBuilding(Long id, BuildingDTO buildingDTO) {
    Building building = getBuilding(id);

    building.setName(buildingDTO.name());
    building.setLocation(locationService.getLocation(buildingDTO.locationId()));

    return buildingRepo.save(building);
  }

  public void deleteBuilding(Long id) {
    buildingRepo.deleteById(id);
  }

  public void deleteBuildings(Set<Long> ids) {
    buildingRepo.deleteAllById(ids);
  }
}
