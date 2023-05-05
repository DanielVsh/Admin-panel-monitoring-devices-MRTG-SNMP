package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.dto.DeviceDTO;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.services.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RestController
@RequestMapping("api/v1/device")
@RequiredArgsConstructor
public class DeviceController {
  private final DeviceService deviceService;

  @GetMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<?> getDevices(
    @PageableDefault(sort = "id", direction = DESC) Pageable page,
    @RequestParam(required = false) String filter
  ) {
    return ResponseEntity.ok(deviceService.getDevices(page, filter));
  }

  @PostMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<?> createDevice(@RequestBody @Valid DeviceDTO deviceDetails) {
    return ResponseEntity.ok(deviceService.createDevice(deviceDetails));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_WRITE')")
  public ResponseEntity<Device> updateDevice(@PathVariable Long id,
                                             @RequestBody @Valid DeviceDTO deviceDetails) {
    return ResponseEntity.ok(deviceService.updateDevice(id, deviceDetails));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
    deviceService.deleteDevice(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping()
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public ResponseEntity<Void> deleteBuildings(@RequestParam Set<Long> ids) {
    deviceService.deleteDevices(ids);
    return ResponseEntity.ok().build();
  }
}
