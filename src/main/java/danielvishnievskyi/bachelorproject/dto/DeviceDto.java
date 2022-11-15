package danielvishnievskyi.bachelorproject.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
