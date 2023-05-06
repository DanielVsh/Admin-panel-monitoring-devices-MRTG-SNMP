package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.entities.Role;
import danielvishnievskyi.bachelorproject.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;


/**
 * Service class for managing Role entities.
 *
 * @author [Daniel Vishnievskyi].
 */
@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepo roleRepo;

  /**
   * Creates a new Role with the given name and set of privileges, if it does not already exist in the database.
   *
   * @param name       the name of the Role to create.
   * @param privileges the set of Privilege objects to assign to the Role.
   * @return the newly created Role object or an existing Role with the same name if found in the database.
   */
  @Transactional
  public Role createIfNotFound(String name, Set<Privilege> privileges) {
    return roleRepo.getByName(name.toUpperCase()).orElseGet(() -> {
      Role role = new Role(name.toUpperCase());
      role.setPrivileges(privileges);
      roleRepo.save(role);
      return role;
    });
  }
}
