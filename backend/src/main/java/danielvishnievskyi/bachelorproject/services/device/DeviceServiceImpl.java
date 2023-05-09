package danielvishnievskyi.bachelorproject.services.device;

import danielvishnievskyi.bachelorproject.dto.DeviceDTO;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.exception.device.DeviceBadRequestException;
import danielvishnievskyi.bachelorproject.exception.device.DeviceNotFoundException;
import danielvishnievskyi.bachelorproject.repositories.DeviceRepo;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.DeviceSpecification;
import danielvishnievskyi.bachelorproject.services.building.BuildingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

import static danielvishnievskyi.bachelorproject.enums.SearchOperation.EQUAL;
import static danielvishnievskyi.bachelorproject.enums.SearchOperation.MATCH;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
  private final DeviceRepo deviceRepo;
  private final BuildingService buildingService;

  @Override
  public Page<Device> getFilteredAndPageableList(Pageable pageable, String filter) {
    DeviceSpecification dvFilter = new DeviceSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      dvFilter.add(new SearchCriteria("id", filter, EQUAL));
    } else if (StringUtils.hasLength(filter)) {
      dvFilter.add(new SearchCriteria("name", filter, MATCH));
    }
    return deviceRepo.findAll(dvFilter, pageable);
  }

  @Override
  public Device getEntityById(Long id) {
    return deviceRepo.findById(id)
      .orElseThrow(() -> new DeviceNotFoundException(String.format("Device[%d]: Not Found", id)));
  }

  @Override
  public Device createEntity(DeviceDTO entityDTO) {
    deviceRepo.findByName(entityDTO.name()).ifPresent(building -> {
      throw new DeviceBadRequestException(String.format("Device %s already exists", entityDTO.name()));
    });

    return deviceRepo.save(
      new Device(
        entityDTO.name(),
        buildingService.getEntityById(entityDTO.buildingId()),
        entityDTO.ipAddress(),
        entityDTO.switchMap(),
        isBlank(entityDTO.SNMP()) ? "public" : entityDTO.SNMP()
      ));
  }

  @Override
  public Device updateEntity(Long id, DeviceDTO entityDTO) {
    Device device = getEntityById(id);
    device.setBuilding(buildingService.getEntityById(entityDTO.buildingId()));
    device.setSNMP(entityDTO.SNMP().toLowerCase());
    device.setName(entityDTO.name());
    device.setIpAddress(entityDTO.ipAddress());
    device.setSwitchMap(entityDTO.switchMap());
    device.setUptime(entityDTO.uptime());
    return deviceRepo.save(device);
  }

  @Override
  public void deleteEntityById(Long id) {
    deviceRepo.deleteById(id);
  }

  @Override
  public void deleteEntitiesByIds(Set<Long> ids) {
    deviceRepo.deleteAllById(ids);
  }
}
