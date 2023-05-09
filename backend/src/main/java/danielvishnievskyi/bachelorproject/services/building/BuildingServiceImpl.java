package danielvishnievskyi.bachelorproject.services.building;

import danielvishnievskyi.bachelorproject.dto.BuildingDTO;
import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.exception.building.BuildingBadRequestException;
import danielvishnievskyi.bachelorproject.exception.building.BuildingNotFoundException;
import danielvishnievskyi.bachelorproject.repositories.BuildingRepo;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.BuildingSpecification;
import danielvishnievskyi.bachelorproject.services.location.LocationService;
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
public class BuildingServiceImpl implements BuildingService {
  private final BuildingRepo buildingRepo;
  private final LocationService locationService;

  @Override
  public Page<Building> getFilteredAndPageableList(Pageable pageable, String filter) {
    BuildingSpecification buildFilter = new BuildingSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      buildFilter.add(new SearchCriteria("id", filter, EQUAL));
    } else if (StringUtils.hasLength(filter)) {
      buildFilter.add(new SearchCriteria("name", filter, MATCH));
    }
    return buildingRepo.findAll(buildFilter, pageable);
  }

  @Override
  public Building getEntityById(Long id) {
    return buildingRepo.findById(id)
      .orElseThrow(() -> new BuildingNotFoundException(String.format("Building[%d]: Not Found", id)));
  }

  @Override
  public Building createEntity(BuildingDTO entityDTO) {
    Location location = locationService.getEntityById(entityDTO.locationId());

    buildingRepo.findByName(entityDTO.name()).ifPresent(building -> {
      throw new BuildingBadRequestException(String.format("Building %s already exists", building.getName()));
    });

    return buildingRepo.save(new Building(entityDTO.name(), location));
  }

  @Override
  public Building updateEntity(Long id, BuildingDTO entityDTO) {
    Building building = getEntityById(id);

    building.setName(entityDTO.name());
    building.setLocation(locationService.getEntityById(entityDTO.locationId()));

    return buildingRepo.save(building);
  }

  @Override
  public void deleteEntityById(Long id) {
    buildingRepo.deleteById(id);
  }

  @Override
  public void deleteEntitiesByIds(Set<Long> ids) {
    buildingRepo.deleteAllById(ids);
  }
}
