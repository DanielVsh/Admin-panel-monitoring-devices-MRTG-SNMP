package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.entities.Role;
import danielvishnievskyi.bachelorproject.implementations.RoleImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleImpl roleImpl;

  public void save(Role role) {
    roleImpl.save(role);
  }

  public void delete(Role role) {
    roleImpl.delete(role);
  }

  public Role createIfNotFound(String name, Collection<Privilege> privileges) {
    return roleImpl.createIfNotFound(name, privileges);
  }
}
