package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.dto.DeviceDTO;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.DeviceSpecification;
import danielvishnievskyi.bachelorproject.services.BuildingService;
import danielvishnievskyi.bachelorproject.services.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static danielvishnievskyi.bachelorproject.enums.SearchOperation.EQUAL;
import static danielvishnievskyi.bachelorproject.enums.SearchOperation.MATCH;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@RestController
@RequestMapping("api/v1/device")
@RequiredArgsConstructor
public class DeviceController {
  private final DeviceService deviceService;
  private final BuildingService buildingService;

  @GetMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<?> getFilteredAndPageableDevices(
    @PageableDefault(sort = "id", direction = DESC) Pageable page,
    @RequestParam(required = false) String filter
  ) {
    DeviceSpecification dvFilter = new DeviceSpecification();

    if (NumberUtils.isParsable(filter) && Long.parseLong(filter) > 0) {
      dvFilter.add(new SearchCriteria("id", filter, EQUAL));
    }  else if (StringUtils.hasLength(filter)) {
      dvFilter.add(new SearchCriteria("name", filter, MATCH));
    }
    return ResponseEntity.ok(deviceService.findAll(dvFilter, page));
  }

  @PostMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<?> createDevice(@RequestBody @Valid DeviceDTO deviceDetails) {
    if (deviceService.findByName(deviceDetails.getName()).isPresent()) {
      return new ResponseEntity<>
        (String.format("Device with %s name already exists", deviceDetails.getName()), CONFLICT);
    }
    if (buildingService.findById(deviceDetails.getBuildingId()).isEmpty()) {
      return new ResponseEntity<>(("Building not found"), CONFLICT);
    }
    Device device = new Device(
      deviceDetails.getName(),
      buildingService.findById(deviceDetails.getBuildingId()).orElseThrow(),
      deviceDetails.getIpAddress(),
      deviceDetails.isSwitchMap(),
      deviceDetails.getSNMP() != null ? deviceDetails.getSNMP() : null
    );
    deviceService.save(device);
    return ResponseEntity.ok(device);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_WRITE')")
  public ResponseEntity<Device> updateDevice(@PathVariable Long id,
                                             @RequestBody @Valid DeviceDTO deviceDetails) {
    Device device = deviceService.findById(id).orElseThrow();
    device.setName(deviceDetails.getName());
    device.setIpAddress(deviceDetails.getIpAddress());
    device.setSwitchMap(deviceDetails.isSwitchMap());
    device.setUptime(deviceDetails.getUptime());
    deviceService.save(device);
    return ResponseEntity.ok(device);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public ResponseEntity<Device> deleteDevice(@PathVariable Long id) {
    deviceService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
