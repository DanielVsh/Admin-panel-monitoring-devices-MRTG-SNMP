package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BuildingRepo extends JpaRepository<Building, Long>,
  JpaSpecificationExecutor<Building> {
  Optional<Building> getByName(String name);
}
