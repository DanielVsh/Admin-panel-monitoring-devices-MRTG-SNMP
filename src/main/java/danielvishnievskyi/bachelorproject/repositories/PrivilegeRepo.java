package danielvishnievskyi.bachelorproject.repositories;


import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import danielvishnievskyi.bachelorproject.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface PrivilegeRepo extends JpaRepository<Privilege, Long>,
  RevisionRepository<Privilege, Long, Long> {
  Optional<Privilege> getByName(String name);
}
