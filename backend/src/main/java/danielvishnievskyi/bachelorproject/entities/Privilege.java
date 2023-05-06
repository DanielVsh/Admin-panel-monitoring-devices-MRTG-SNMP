package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


/**
 * A class representing a privilege entity in the system.
 * Each privilege represents a certain permission that can be granted to a role.
 *
 * @author [Daniel Vishnievskyi].
 */
@Entity
@Setter
@Getter
@ToString
@Embeddable
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Privilege {

  /**
   * The unique identifier of this privilege.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The name of a privilege.
   */
  private String name;

  /**
   * Constructs a new privilege with the given name.
   *
   * @param name The name of the privilege.
   */
  public Privilege(String name) {
    this.name = name;
  }
}
