package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.services.BuildingService;
import danielvishnievskyi.bachelorproject.services.DeviceService;
import danielvishnievskyi.bachelorproject.dto.DeviceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@RestController
@PreAuthorize("hasAnyRole('SUPER_ADMIN')")
@RequestMapping("api/v1/device")
@RequiredArgsConstructor
public class DeviceController {
  private final DeviceService deviceService;
  private final BuildingService buildingService;

  @GetMapping()
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Collection<Device>> getDevices() {
    return ResponseEntity.ok(deviceService.getAll());
  }

  @PostMapping()
  public ResponseEntity<?> createDevice(@RequestBody @Valid DeviceDto deviceDetails) {
    if (deviceService.getByName(deviceDetails.getName()).isPresent()) {
      return new ResponseEntity<>
        (String.format("Device with %s name already exists", deviceDetails.getName()), CONFLICT);
    }
    if (buildingService.getById(deviceDetails.getBuildingId()).isEmpty()) {
      return new ResponseEntity<>(("Building not found"), CONFLICT);
    }
    Device device = new Device(
      deviceDetails.getName(),
      buildingService.getById(deviceDetails.getBuildingId()).orElseThrow(),
      deviceDetails.getIpAddress(),
      deviceDetails.isSwitchMap(),
      deviceDetails.getSNMP() != null ? deviceDetails.getSNMP() : null
    );
    deviceService.save(device);
    return ResponseEntity.ok(device);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Device> updateDevice(@PathVariable Long id,
                                             @RequestBody @Valid DeviceDto deviceDetails) {
    Device device = deviceService.getById(id).orElseThrow();
    device.setName(deviceDetails.getName());
    device.setIpAddress(deviceDetails.getIpAddress());
    device.setSwitchMap(deviceDetails.isSwitchMap());
    device.setUptime(deviceDetails.getUptime());
    deviceService.save(device);
    return ResponseEntity.ok(device);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Device> deleteDevice(@PathVariable Long id) {
    deviceService.delete(id);
    return ResponseEntity.ok().build();
  }
}
