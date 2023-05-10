package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.Location;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The LocationRepository interface provides the CRUD operations for the Location entity.
 * It extends the JpaRepository interface, JpaSpecificationExecutor interface, and is annotated with @Repository.
 * It also utilizes the @JaversSpringDataAuditable annotation for auditing purposes.
 *
 * @author [Daniel Vishnievskyi].
 */
@Repository
@JaversSpringDataAuditable
public interface LocationRepository extends JpaRepository<Location, Long>,
  JpaSpecificationExecutor<Location> {

  /**
   * Retrieves an optional Location instance based on the given name.
   *
   * @param name The name to search for.
   * @return An Optional containing the found Location instance, or an empty Optional if none was found.
   */
  Optional<Location> findByName(String name);
}
