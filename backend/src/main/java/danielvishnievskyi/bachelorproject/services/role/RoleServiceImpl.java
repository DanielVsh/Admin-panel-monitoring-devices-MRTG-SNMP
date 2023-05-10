package danielvishnievskyi.bachelorproject.services.role;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.entities.Role;
import danielvishnievskyi.bachelorproject.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
  private final RoleRepository roleRepository;

  @Transactional
  @Override
  public Role createIfNotFound(String name, Set<Privilege> privileges) {
    return roleRepository.getByName(name.toUpperCase()).orElseGet(() -> {
      Role role = new Role(name.toUpperCase());
      role.setPrivileges(privileges);
      roleRepository.save(role);
      return role;
    });
  }
}
