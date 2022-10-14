package danielvishnievskyi.bachelorproject.repositories;


import danielvishnievskyi.bachelorproject.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
  Role findRoleByName(String name);
}
