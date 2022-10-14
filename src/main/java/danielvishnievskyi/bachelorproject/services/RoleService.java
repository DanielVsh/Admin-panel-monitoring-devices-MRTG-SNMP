package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.entities.Role;
import danielvishnievskyi.bachelorproject.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepo roleRepo;

  public void saveRole(Role role) {
    roleRepo.save(role);
  }

  @Transactional
  public Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
    Role role = roleRepo.findRoleByName(name);
    if (role == null) {
      role = new Role(name);
      role.setPrivileges(privileges);
      saveRole(role);
    }
    return role;
  }
}
