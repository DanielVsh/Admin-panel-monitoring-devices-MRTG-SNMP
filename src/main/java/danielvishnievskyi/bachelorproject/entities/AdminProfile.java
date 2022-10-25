package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@Transactional
@NoArgsConstructor
public class AdminProfile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false)
  private String firstname;
  @Column(nullable = false)
  private String lastname;
  @Column(nullable = false)
  @JsonIgnore
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JsonIgnore
  @JoinTable(
    name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;

  public AdminProfile(String username,
                      String firstname,
                      String lastname,
                      String password,
                      Collection<Role> roles) {
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
    this.password = password;
    this.roles = roles;
  }

  public Collection<String> getRolesName() {
    return roles.stream().map(Role::getName).collect(Collectors.toList());
  }
}
