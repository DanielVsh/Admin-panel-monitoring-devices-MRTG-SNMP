package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.dto.DeviceDTO;
import danielvishnievskyi.bachelorproject.entities.Device;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

import static danielvishnievskyi.bachelorproject.enums.SearchOperation.EQUAL;
import static danielvishnievskyi.bachelorproject.enums.SearchOperation.MATCH;

/**
 * Service class that handles operations related to devices.
 *
 * @author [Daniel Vishnievskyi].
 */
@Service
@RequiredArgsConstructor
public class DeviceService {
  private final DeviceRepo deviceRepo;
  private final BuildingService buildingService;

  /**
   * Returns a page of devices that match the given filter and pagination information.
   *
   * @param pageable the pagination information
   * @param filter   the filter to apply to the devices
   * @return a page of devices that match the given filter and pagination information
   */
  public Page<Device> getDevices(Pageable pageable, String filter) {
    DeviceSpecification dvFilter = new DeviceSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      dvFilter.add(new SearchCriteria("id", filter, EQUAL));
    } else if (StringUtils.hasLength(filter)) {
      dvFilter.add(new SearchCriteria("name", filter, MATCH));
    }
    return deviceRepo.findAll(dvFilter, pageable);
  }

  /**
   * Returns the device with the given ID, or throws a {@link DeviceNotFoundException} if it is not found.
   *
   * @param id the ID of the device to return
   * @return the device with the given ID
   * @throws DeviceNotFoundException if the device with the given ID is not found
   */
  public Device getDevice(Long id) {
    return deviceRepo.findById(id)
      .orElseThrow(() -> new DeviceNotFoundException(String.format("Device[%d]: Not Found", id)));
  }

  /**
   * Creates a new device with the given information, and returns the created device.
   *
   * @param deviceDTO the information to use when creating the device
   * @return the created device
   * @throws DeviceBadRequestException if a device with the same name already exists
   * @throws BuildingNotFoundException if the building with the given ID is not found
   */
  public Device createDevice(DeviceDTO deviceDTO) {
    deviceRepo.findByName(deviceDTO.name()).ifPresent(building -> {
      throw new DeviceBadRequestException(String.format("Device %s already exists", deviceDTO.name()));
    });

    return deviceRepo.save(
      new Device(
        deviceDTO.name(),
        buildingService.getBuilding(deviceDTO.buildingId()),
        deviceDTO.ipAddress(),
        deviceDTO.switchMap(),
        deviceDTO.SNMP() != null ? deviceDTO.SNMP() : null
      ));
  }

  /**
   * Updates the device with the given ID using the information in the given DTO, and returns the updated device.
   *
   * @param id        the ID of the device to update
   * @param deviceDTO the information to use when updating the device
   * @return the updated device
   * @throws DeviceNotFoundException   if the device with the given ID is not found
   * @throws BuildingNotFoundException if the building with the given ID is not found
   */
  public Device updateDevice(Long id, DeviceDTO deviceDTO) {
    Device device = getDevice(id);
    device.setBuilding(buildingService.getBuilding(deviceDTO.buildingId()));
    device.setName(deviceDTO.name());
    device.setIpAddress(deviceDTO.ipAddress());
    device.setSwitchMap(deviceDTO.switchMap());
    device.setUptime(deviceDTO.uptime());
    return deviceRepo.save(device);
  }

  /**
   * Deletes the device with the given ID.
   *
   * @param id the ID of the device to delete
   */
  public void deleteDevice(Long id) {
    deviceRepo.deleteById(id);
  }

  /**
   * Deletes the devices with the given IDs.
   *
   * @param ids the IDs of the devices to delete
   */
  public void deleteDevices(Set<Long> ids) {
    deviceRepo.deleteAllById(ids);
  }
}
