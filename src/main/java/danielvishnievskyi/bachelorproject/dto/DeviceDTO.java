package danielvishnievskyi.bachelorproject.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class DeviceDTO {
  @NotBlank(message = "name should not be empty")
  private String name;
  //TODO: annotation for ipAddress
  private String ipAddress;
  private Date uptime;
  private boolean switchMap;
  private String SNMP;
  @Min(value = 1, message = "id should be valid")
  private Long buildingId;
}
