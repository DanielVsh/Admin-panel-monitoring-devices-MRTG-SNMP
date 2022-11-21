package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.EAGER;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Location extends Auditable<String> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;
  @OneToMany(fetch = EAGER, cascade = MERGE, orphanRemoval = true, mappedBy = "location")
  private Collection<Building> buildings;

  public Location(String name) {
    this.name = name;
  }

}
