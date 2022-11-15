package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Device {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;
  @Column(unique = true)
  private String ipAddress;
  private LocalDateTime uptime;
  private boolean switchMap;
  private String SNMP;

  @ManyToOne()
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private Building building;

  public Device(String name,
                Building building,
                String ipAddress,
                boolean switchMap) {
    this.name = name;
    this.building = building;
    this.ipAddress = ipAddress;
    this.uptime = LocalDateTime.now();
    this.switchMap = switchMap;
    this.SNMP = "PUBLIC";
  }
  public Device(String name,
                Building building,
                String ipAddress,
                boolean switchMap,
                String SNMP) {
    this.name = name;
    this.building = building;
    this.ipAddress = ipAddress;
    this.uptime = LocalDateTime.now();
    this.switchMap = switchMap;
    this.SNMP = SNMP;
  }
}
