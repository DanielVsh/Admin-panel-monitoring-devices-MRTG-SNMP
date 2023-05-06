package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents the entity of a Device in the system.
 * This class is used to model a device located in a building.
 *
 * @author [Daniel Vishnievskyi]
 */
@Entity
@Getter
@Setter
@Embeddable
@ToString
@NoArgsConstructor
@JsonIdentityInfo(generator = JSOGGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Device {

  /**
   * The unique identifier for a Device.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The unique name of the Device.
   */
  @Column(unique = true)
  private String name;

  /**
   * The unique IP address of the Device.
   */
  @Column(unique = true)
  private String ipAddress;

  /**
   * The date and time when the Device was last restarted.
   */
  private Date uptime;

  /**
   * A flag that indicates whether the Device is currently in use.
   */
  private boolean switchMap;

  /**
   * The SNMP (Simple Network Management Protocol) community string for the Device.
   */
  private String SNMP;

  /**
   * The Building where the Device is located.
   */
  @ManyToOne()
  @ToString.Exclude
  private Building building;

  /**
   * Constructs a Device object with the specified parameters.
   * The SNMP value is set to "public" by default.
   *
   * @param name      the unique name of the Device
   * @param building  the Building where the Device is located
   * @param ipAddress the IP address of the Device
   * @param switchMap a flag that indicates whether the Device is currently in use
   */
  public Device(String name,
                Building building,
                String ipAddress,
                boolean switchMap) {
    this.name = name;
    this.building = building;
    this.ipAddress = ipAddress;
    this.uptime = new Date();
    this.switchMap = switchMap;
    this.SNMP = "public";
  }

  /**
   * Constructs a Device object with the specified parameters.
   *
   * @param name      the unique name of the Device
   * @param building  the Building where the Device is located
   * @param ipAddress the IP address of the Device
   * @param switchMap a flag that indicates whether the Device is currently in use
   * @param SNMP      the SNMP community string for the Device
   */
  public Device(String name,
                Building building,
                String ipAddress,
                boolean switchMap,
                String SNMP) {
    this.name = name;
    this.building = building;
    this.ipAddress = ipAddress;
    this.uptime = new Date();
    this.switchMap = switchMap;
    this.SNMP = SNMP.toLowerCase();
  }
}

