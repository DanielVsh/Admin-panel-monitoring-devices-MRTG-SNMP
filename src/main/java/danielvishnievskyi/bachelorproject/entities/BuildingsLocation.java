package danielvishnievskyi.bachelorproject.entities;

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
@NoArgsConstructor
public class BuildingsLocation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  private Collection<Building> buildings;

  public BuildingsLocation(String name) {
    this.name = name;
  }
}
