package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.EAGER;

/**
 * Represents the entity of a Building in the system.
 * This class is used to model a building in a location.
 *
 * @author [Daniel Vishnievskyi].
 */
@Entity
@Getter
@Setter
@ToString
@Embeddable
@NoArgsConstructor
@JsonIdentityInfo(generator = JSOGGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Building {

  /**
   * The unique identifier for a Building.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The unique name of the Building.
   */
  @Column(unique = true)
  private String name;

  /**
   * The Location where the Building is located.
   */
  @ManyToOne()
  @ToString.Exclude
  private Location location;

  /**
   * The list of Devices that are located in the Building.
   * A Building can have multiple Devices.
   */
  @OneToMany(fetch = EAGER, cascade = MERGE, orphanRemoval = true, mappedBy = "building")
  private List<Device> devices;

  /**
   * Constructs a Building object with the specified parameters.
   *
   * @param name     the unique name of the Building
   * @param location the Location where the Building is located
   */
  public Building(String name, Location location) {
    this.name = name;
    this.location = location;
  }
}
