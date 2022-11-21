package danielvishnievskyi.bachelorproject.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role extends Auditable<String> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  @ManyToMany(mappedBy = "roles")
  private Collection<AdminProfile> users;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "roles_privileges",
    joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
  private Collection<Privilege> privileges;

  public Role(String name) {
    if (name.contains("ROLE_")) {
      this.name = name;
    } else {
      this.name = "ROLE_" + name;
    }
  }
}
