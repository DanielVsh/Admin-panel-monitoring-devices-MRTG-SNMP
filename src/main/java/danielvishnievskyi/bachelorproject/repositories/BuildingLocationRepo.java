package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingLocationRepo extends JpaRepository<Location, Long> {
  Optional<Location> getByName(String name);
}
