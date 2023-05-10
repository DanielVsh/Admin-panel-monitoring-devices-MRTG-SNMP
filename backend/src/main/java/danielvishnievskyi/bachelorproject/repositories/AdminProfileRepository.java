package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The AdminProfileRepository interface provides the CRUD operations for the AdminProfile entity.
 * It extends the JpaRepository interface and is annotated with @Repository.
 *
 * @author [Daniel Vishnievskyi].
 */
@Repository
public interface AdminProfileRepository extends JpaRepository<AdminProfile, Long> {

  /**
   * Retrieves an optional AdminProfile instance based on the given username.
   *
   * @param username The username to search for.
   * @return An Optional containing the found AdminProfile instance, or an empty Optional if none was found.
   */
  Optional<AdminProfile> getByUsername(String username);
}
