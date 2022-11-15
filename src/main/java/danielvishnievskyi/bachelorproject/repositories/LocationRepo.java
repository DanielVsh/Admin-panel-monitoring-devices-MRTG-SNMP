package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface LocationRepo extends JpaRepository<Location, Long>,
  JpaSpecificationExecutor<Location> {
  Optional<Location> getByName(String name);
}
