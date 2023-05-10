package danielvishnievskyi.bachelorproject.repositories;


import danielvishnievskyi.bachelorproject.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The PrivilegeRepository interface provides the CRUD operations for the Privilege entity.
 * It extends the JpaRepository interface and is annotated with @Repository.
 *
 * @author [Daniel Vishnievskyi].
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

  /**
   * Retrieves an optional Privilege instance based on the given name.
   *
   * @param name The name to search for.
   * @return An Optional containing the found Privilege instance, or an empty Optional if none was found.
   */
  Optional<Privilege> getByName(String name);
}
