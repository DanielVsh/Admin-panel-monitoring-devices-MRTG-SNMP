package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Getter
@Setter
@ToString
@Embeddable
@NoArgsConstructor
@JsonIdentityInfo(generator = JSOGGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Building  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;

  @ManyToOne()
  @ToString.Exclude
  private Location location;

  @OneToMany(fetch = EAGER, cascade = MERGE, orphanRemoval = true, mappedBy = "building")
  private Collection<Device> devices;

  public Building(String name, Location location) {
    this.name = name;
    this.location = location;
  }
}
