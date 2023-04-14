package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@Embeddable
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  @ManyToMany(mappedBy = "roles")
  @ToString.Exclude
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
