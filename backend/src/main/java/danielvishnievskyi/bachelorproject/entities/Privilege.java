package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Setter
@Getter
@ToString
@Embeddable
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Privilege  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "privileges")
  private Collection<Role> roles;

  public Privilege(String name) {
    this.name = name;
  }
}
