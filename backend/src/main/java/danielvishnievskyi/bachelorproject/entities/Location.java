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
 * Represents the entity of a Location in the system.
 * It contains a unique name and a list of buildings associated with that location.
 *
 * @author [Daniel Vishnievskyi].
 */
@Entity
@Setter
@Getter
@ToString
@Embeddable
@NoArgsConstructor
@JsonIdentityInfo(generator = JSOGGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Location {

  /**
   * The unique ID for a Location.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The unique name of a Location.
   */
  @Column(unique = true)
  private String name;

  /**
   * The list of buildings associated with a Location.
   */
  @OneToMany(fetch = EAGER, cascade = MERGE, orphanRemoval = true, mappedBy = "location")
  private List<Building> buildings;

  /**
   * Constructs a new Location with the specified name.
   *
   * @param name the name of the location
   */
  public Location(String name) {
    this.name = name;
  }
}
