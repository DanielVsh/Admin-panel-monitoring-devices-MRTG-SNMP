package danielvishnievskyi.bachelorproject.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

public record DeviceDTO(
  @NotBlank(message = "Name should not be empty") String name,
  String ipAddress,
  Date uptime,
  boolean switchMap,
  String SNMP,
  @Min(value = 1, message = "Id should be valid") Long buildingId
) { }
