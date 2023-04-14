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

  @Transactional
  public Role createIfNotFound(String name, Collection<Privilege> privileges) {
    return roleRepo.getByName(name.toUpperCase()).orElseGet(() -> {
      Role role = new Role(name.toUpperCase());
      role.setPrivileges(privileges);
      roleRepo.save(role);
      return role;
    });
  }
}
