package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Audited
@JsonIdentityInfo(generator = JSOGGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Device  {
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
