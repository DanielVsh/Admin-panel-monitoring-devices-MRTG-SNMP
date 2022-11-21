package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Building extends Auditable<String> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;

  @ManyToOne()
  private Location location;

  @OneToMany(fetch = EAGER, cascade = MERGE, orphanRemoval = true, mappedBy = "building")
  private Collection<Device> devices;

  public Building(String name, Location location) {
    this.name = name;
    this.location = location;
  }
}
