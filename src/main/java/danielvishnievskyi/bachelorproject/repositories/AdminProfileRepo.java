package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminProfileRepo extends JpaRepository<AdminProfile, Long>,
  RevisionRepository<AdminProfile, Long, Long> {
  Optional<AdminProfile> getByUsername(String username);
}
