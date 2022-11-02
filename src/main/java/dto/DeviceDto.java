package dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceDto {
  private Long id;
  private String name;
  private String ipAddress;
  private LocalDateTime uptime;
  private boolean switchMap;
  private String SNMP;
  private Long buildingId;
}
