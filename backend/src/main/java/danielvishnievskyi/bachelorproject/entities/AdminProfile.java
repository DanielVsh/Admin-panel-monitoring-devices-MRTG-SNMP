package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the entity of an AdminProfile in the system.
 * This class is used to model the user account of an admin.
 *
 * @author [Daniel Vishnievskyi].
 */
@Entity
@Getter
@Setter
@ToString
@Embeddable
@Transactional
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AdminProfile {

  /**
   * The unique identifier for an AdminProfile.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  /**
   * The unique username for the AdminProfile.
   */
  @Column(nullable = false, unique = true)
  private String username;

  /**
   * The first name of the AdminProfile.
   */
  @Column(nullable = false)
  private String firstname;

  /**
   * The last name of the AdminProfile.
   */
  @Column(nullable = false)
  private String lastname;

  /**
   * The hashed password of the AdminProfile.
   * This is stored in the database as a one-way hash for security reasons.
   */
  @Column(nullable = false)
  @JsonIgnore
  private String password;

  /**
   * The set of roles that the AdminProfile has.
   * An AdminProfile can have multiple roles.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JsonIgnore
  @JoinTable(
    name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Set<Role> roles;

  /**
   * Constructs an AdminProfile object with the specified parameters.
   *
   * @param username  the unique username of the AdminProfile
   * @param firstname the first name of the AdminProfile
   * @param lastname  the last name of the AdminProfile
   * @param password  the hashed password of the AdminProfile
   * @param roles     the set of roles that the AdminProfile has
   */
  public AdminProfile(String username,
                      String firstname,
                      String lastname,
                      String password,
                      Set<Role> roles) {
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
    this.password = password;
    this.roles = roles;
  }

  /**
   * @return the set of role names that the AdminProfile has
   */
  public Set<String> getRolesName() {
    return roles.stream().map(Role::getName).collect(Collectors.toSet());
  }
}
