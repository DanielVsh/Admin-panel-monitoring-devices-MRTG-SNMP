package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.dto.DeviceDTO;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.services.device.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * The DeviceController class is responsible for handling all incoming requests related to devices and forwarding them to
 * the DeviceService for processing.
 *
 * @author [Daniel Vishnievskyi]
 */
@RestController
@RequestMapping("api/v1/device")
@RequiredArgsConstructor
public class DeviceController {

  /**
   * The deviceService instance used to perform operations related to Device entity.
   */
  private final DeviceService deleteDevice;

  /**
   * Retrieves a page of devices based on the provided filter and pagination parameters.
   *
   * @param page   The pagination parameters including page number, size and sorting criteria.
   * @param filter An optional filter to apply on the results.
   * @return ResponseEntity with the retrieved devices and pagination information.
   */
  @GetMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Page<Device>> getDevices(
    @PageableDefault(sort = "id", direction = DESC) Pageable page,
    @RequestParam(required = false) String filter
  ) {
    return ResponseEntity.ok(deleteDevice.getFilteredAndPageableList(page, filter));
  }

  /**
   * Returns a device with the given ID.
   *
   * @param id the ID of the device to retrieve
   * @return a response entity containing the retrieved device
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Device> getDevice(@PathVariable Long id) {
    return ResponseEntity.ok(deleteDevice.getEntityById(id));
  }

  /**
   * Creates a new device based on the provided device details.
   *
   * @param deviceDetails The details of the device to be created.
   * @return ResponseEntity with the created device and its location URI.
   */
  @PostMapping()
  @PreAuthorize("hasAnyRole('ADMIN_VIEW')")
  public ResponseEntity<Device> createDevice(@RequestBody @Valid DeviceDTO deviceDetails) {
    return ResponseEntity.ok(deleteDevice.createEntity(deviceDetails));
  }

  /**
   * Updates an existing device based on the provided device details and id.
   *
   * @param id            The id of the device to be updated.
   * @param deviceDetails The updated details of the device.
   * @return ResponseEntity with the updated device.
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN_WRITE')")
  public ResponseEntity<Device> updateDevice(@PathVariable Long id,
                                             @RequestBody @Valid DeviceDTO deviceDetails) {
    return ResponseEntity.ok(deleteDevice.updateEntity(id, deviceDetails));
  }

  /**
   * Deletes an existing device based on the provided id.
   *
   * @param id The id of the device to be deleted.
   * @return ResponseEntity with no content.
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
    deleteDevice.deleteEntityById(id);
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes multiple existing devices based on the provided ids.
   *
   * @param ids The ids of the devices to be deleted.
   * @return ResponseEntity with no content.
   */
  @DeleteMapping()
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public ResponseEntity<Void> deleteBuildings(@RequestParam Set<Long> ids) {
    deleteDevice.deleteEntitiesByIds(ids);
    return ResponseEntity.ok().build();
  }
}
