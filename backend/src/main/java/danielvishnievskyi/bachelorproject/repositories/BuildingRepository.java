package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.Building;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The BuildingRepository interface provides the CRUD operations for the Building entity.
 * It extends the JpaRepository interface, JpaSpecificationExecutor interface, and is annotated with @Repository.
 * It also utilizes the @JaversSpringDataAuditable annotation for auditing purposes.
 *
 * @author [Daniel Vishnievskyi].
 */
@Repository
@JaversSpringDataAuditable
public interface BuildingRepository extends JpaRepository<Building, Long>,
  JpaSpecificationExecutor<Building> {

  /**
   * Retrieves an optional Building instance based on the given name.
   *
   * @param name The name to search for.
   * @return An Optional containing the found Building instance, or an empty Optional if none was found.
   */
  Optional<Building> findByName(String name);
}
