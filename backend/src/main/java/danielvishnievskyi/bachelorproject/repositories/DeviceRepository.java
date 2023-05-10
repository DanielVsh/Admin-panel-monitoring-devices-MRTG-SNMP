package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.Device;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The DeviceRepository interface provides the CRUD operations for the Device entity.
 * It extends the JpaRepository interface, JpaSpecificationExecutor interface, and is annotated with @Repository.
 * It also utilizes the @JaversSpringDataAuditable annotation for auditing purposes.
 *
 * @author [Daniel Vishnievskyi].
 */
@Repository
@JaversSpringDataAuditable
public interface DeviceRepository extends JpaRepository<Device, Long>,
  JpaSpecificationExecutor<Device> {

  /**
   * Retrieves an optional Device instance based on the given name.
   *
   * @param name The name to search for.
   * @return An Optional containing the found Device instance, or an empty Optional if none was found.
   */
  Optional<Device> findByName(String name);
}
