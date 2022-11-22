package danielvishnievskyi.bachelorproject.repositories;


import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import danielvishnievskyi.bachelorproject.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long>,
  RevisionRepository<Role, Long, Long> {
  Optional<Role> getByName(String name);
}
