package danielvishnievskyi.bachelorproject.repositories;


import danielvishnievskyi.bachelorproject.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * The RoleRepo interface provides the CRUD operations for the Role entity.
 * It extends the JpaRepository interface and is annotated with @Repository.
 *
 * @author [Daniel Vishnievskyi].
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

  /**
   * Retrieves an optional Role instance based on the given name.
   *
   * @param name The name to search for.
   * @return An Optional containing the found Role instance, or an empty Optional if none was found.
   */
  Optional<Role> getByName(String name);
}
