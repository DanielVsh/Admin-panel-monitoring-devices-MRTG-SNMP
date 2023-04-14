package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.Building;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface BuildingRepo extends JpaRepository<Building, Long>,
  JpaSpecificationExecutor<Building>{
  Optional<Building> findByName(String name);
}
