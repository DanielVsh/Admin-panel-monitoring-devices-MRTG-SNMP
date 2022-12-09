package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.entities.Role;
import danielvishnievskyi.bachelorproject.repositories.RoleRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RoleImpl {
  private final RoleRepo roleRepo;

  public void save(Role role) {
    roleRepo.save(role);
  }

  public void delete(Role role) {
    roleRepo.delete(role);
  }

  @Transactional
  public Role createIfNotFound(String name, Collection<Privilege> privileges) {
    return roleRepo.getByName(name.toUpperCase()).orElseGet(() -> {
      Role role = new Role(name.toUpperCase());
      role.setPrivileges(privileges);
      save(role);
      return role;
    });
  }
}
