package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Represents a role for an admin user.
 *
 * @author [Daniel Vishnievskyi].
 */
@Entity
@Getter
@Setter
@ToString
@Embeddable
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role {

  /**
   * The unique identifier of the role.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The name of the role.
   */
  private String name;

  /**
   * The users associated with this role.
   */
  @ManyToMany(mappedBy = "roles")
  @ToString.Exclude
  private List<AdminProfile> users;

  /**
   * The privileges associated with this role.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "roles_privileges",
    joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
  private Set<Privilege> privileges;

  /**
   * Creates a new role with the given name.
   * If the name does not begin with "ROLE_", it will be prepended to the name.
   *
   * @param name the name of the role.
   */
  public Role(String name) {
    if (name.contains("ROLE_")) {
      this.name = name;
    } else {
      this.name = "ROLE_" + name;
    }
  }
}
