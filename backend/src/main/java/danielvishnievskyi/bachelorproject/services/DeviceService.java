package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.dto.DeviceDTO;
import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.exception.building.BuildingBadRequestException;
import danielvishnievskyi.bachelorproject.exception.building.BuildingNotFoundException;
import danielvishnievskyi.bachelorproject.exception.device.DeviceBadRequestException;
import danielvishnievskyi.bachelorproject.exception.device.DeviceNotFoundException;
import danielvishnievskyi.bachelorproject.repositories.DeviceRepo;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.DeviceSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static danielvishnievskyi.bachelorproject.enums.SearchOperation.EQUAL;
import static danielvishnievskyi.bachelorproject.enums.SearchOperation.MATCH;
import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
public class DeviceService{
  private final DeviceRepo deviceRepo;
  private final BuildingService buildingService;


  public Page<Device> getDevices(Pageable pageable, String filter) {
    DeviceSpecification dvFilter = new DeviceSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      dvFilter.add(new SearchCriteria("id", filter, EQUAL));
    }  else if (StringUtils.hasLength(filter)) {
      dvFilter.add(new SearchCriteria("name", filter, MATCH));
    }
    return deviceRepo.findAll(dvFilter, pageable);
  }

  public Device getDevice(Long id) {
    return deviceRepo.findById(id)
      .orElseThrow(() -> new DeviceNotFoundException(String.format("Device[%d]: Not Found", id)));
  }

  public Device createDevice(DeviceDTO deviceDTO) {
    deviceRepo.findByName(deviceDTO.name()).ifPresent(building -> {
      throw new DeviceBadRequestException(String.format("Device %s already exists", deviceDTO.name()));
    });

    Building building = buildingService.getBuilding(deviceDTO.buildingId());

    return deviceRepo.save(
      new Device(
        deviceDTO.name(),
        building,
        deviceDTO.ipAddress(),
        deviceDTO.switchMap(),
        deviceDTO.SNMP() != null ? deviceDTO.SNMP() : null
    ));
  }

  public Device updateDevice(Long id, DeviceDTO deviceDTO) {
    Device device = getDevice(id);
    device.setName(deviceDTO.name());
    device.setIpAddress(deviceDTO.ipAddress());
    device.setSwitchMap(deviceDTO.switchMap());
    device.setUptime(deviceDTO.uptime());
    return deviceRepo.save(device);
  }

  public void deleteDevice(Long id) {
    deviceRepo.deleteById(id);
  }

  public void deleteDevices(Set<Long> ids) {
    deviceRepo.deleteAllById(ids);
  }
}
