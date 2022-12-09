package danielvishnievskyi.bachelorproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceDto {
  @NotBlank(message = "name should not be empty")
  private String name;
  //TODO: annotation for ipAddress
  private String ipAddress;
  private LocalDateTime uptime;
  private boolean switchMap;
  private String SNMP;
  @Min(value = 1, message = "id should be valid")
  private Long buildingId;
}
