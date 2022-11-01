package danielvishnievskyi.bachelorproject.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Device {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String ipAddress;
  private LocalDateTime uptime;
  private boolean switchMap;
  private String SNMP;

  public Device(String name,
                String ipAddress,
                boolean switchMap) {
    this.name = name;
    this.ipAddress = ipAddress;
    this.uptime = LocalDateTime.now();
    this.switchMap = switchMap;
    this.SNMP = "PUBLIC";
  }

  public Device(String name,
                String ipAddress,
                boolean switchMap,
                String SNMP) {
    this.name = name;
    this.ipAddress = ipAddress;
    this.uptime = LocalDateTime.now();
    this.switchMap = switchMap;
    this.SNMP = SNMP;
  }
}
