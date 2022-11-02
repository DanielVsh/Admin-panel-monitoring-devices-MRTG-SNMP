package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.services.DeviceBuildingLocationService;
import danielvishnievskyi.bachelorproject.services.DeviceService;
import dto.DeviceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@Slf4j
@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("api/v1/device")
@RequiredArgsConstructor
public class DeviceController {
  private final DeviceService deviceService;
  private final DeviceBuildingLocationService devBuildLocService;

  @GetMapping()
  public ResponseEntity<Collection<Device>> getDevices() {
    return ResponseEntity.ok().body(deviceService.getDevices());
  }

  @GetMapping("/getByBuilding/{buildingId}")
  public ResponseEntity<Collection<Device>> getDevicesByBuilding(@PathVariable Long buildingId) {
    return ResponseEntity.ok().body(devBuildLocService.getDevicesByBuilding(buildingId));
  }

  @PostMapping("/create")
  public ResponseEntity<Device> createDevice(@RequestBody DeviceDto deviceDetails) {
    if (deviceDetails.getName() == null) {
      return ResponseEntity.badRequest().build();
    }
    Device device = new Device(
      deviceDetails.getName(),
      deviceDetails.getIpAddress(),
      deviceDetails.isSwitchMap(),
      deviceDetails.getSNMP());
    deviceService.save(device);
    devBuildLocService.joinDevicesToBuilding(Set.of(device.getId()), deviceDetails.getBuildingId());
    return ResponseEntity.ok().body(device);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Device> updateDevice(@PathVariable Long id,
      @RequestBody Device deviceDetails) {
    Device device = deviceService.getById(id);
    device.setName(deviceDetails.getName());
    device.setIpAddress(deviceDetails.getIpAddress());
    device.setSwitchMap(deviceDetails.isSwitchMap());
    device.setUptime(deviceDetails.getUptime());
    deviceService.save(device);
    return ResponseEntity.ok().body(device);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Device> deleteDevice(@PathVariable Long id) {
    deviceService.delete(id);
    return ResponseEntity.ok().build();
  }
}
