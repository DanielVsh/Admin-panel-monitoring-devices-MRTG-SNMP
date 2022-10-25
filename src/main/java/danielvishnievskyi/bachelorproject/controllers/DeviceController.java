package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.services.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("api/v1/device")
@RequiredArgsConstructor
public class DeviceController {
  private final DeviceService deviceService;

  @GetMapping()
  public ResponseEntity<Collection<Device>> getDevices() {
    return ResponseEntity.ok().body(deviceService.getDevices());
  }

  @GetMapping("/getByBuilding/{buildingId}")
  public ResponseEntity<Collection<Device>> getDeviceByBuilding(@PathVariable Long buildingId) {
    return ResponseEntity.ok().body(deviceService.getByBuilding(buildingId));
  }

  @PostMapping("/create")
  public ResponseEntity<Device> createDevice(@RequestBody Device deviceDetails) {
    if (deviceDetails.getName() == null) {
      return ResponseEntity.badRequest().build();
    }
    deviceService.save(deviceDetails);
    return ResponseEntity.ok().body(deviceDetails);
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
    deviceService.deleteFromBuilding(id);
    return ResponseEntity.ok().build();
  }
}
