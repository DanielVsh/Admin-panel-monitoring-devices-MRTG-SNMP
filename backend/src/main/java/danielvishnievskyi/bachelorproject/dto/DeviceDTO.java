package danielvishnievskyi.bachelorproject.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Data transfer object (DTO) for creating or updating a device. Contains the following parameters:
 * <p>
 * The {@code name}, {@code ipAddress}, {@code switchMap}, {@code buildingId} parameters is required and should not be blank. The {@code uptime} parameter are optional.
 * The {@code buildingId} should be a positive integer value.
 *
 * @param name       a non-blank string representing the name of the device
 * @param ipAddress  a non-blank string representing the IP address of the device
 * @param uptime     a {@code Date} representing the uptime of the device
 * @param switchMap  a not-null boolean value indicating whether the device is switch map enabled or not
 * @param SNMP       a string representing the SNMP of the device
 * @param buildingId a positive integer value representing the ID of the building where the device is installed
 * @author [Daniel Vishnievskyi].
 */
public record DeviceDTO(
  @NotBlank(message = "Name should not be empty") String name,
  @NotBlank(message = "Ip address should not be empty") String ipAddress,
  Date uptime,
  @NotNull boolean switchMap,
  String SNMP,
  @Min(value = 1, message = "Id should be valid") Long buildingId
) {
}
