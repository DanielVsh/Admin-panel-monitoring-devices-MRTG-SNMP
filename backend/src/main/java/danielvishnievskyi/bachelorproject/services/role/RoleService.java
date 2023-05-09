package danielvishnievskyi.bachelorproject.services.role;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.entities.Role;

import java.util.Set;

/**
 * Service class for managing Role entities.
 *
 * @author [Daniel Vishnievskyi].
 */
public interface RoleService {

  /**
   * Creates a new Role with the given name and set of privileges, if it does not already exist in the database.
   *
   * @param name       the name of the Role to create.
   * @param privileges the set of Privilege objects to assign to the Role.
   * @return the newly created Role object or an existing Role with the same name if found in the database.
   */
  Role createIfNotFound(String name, Set<Privilege> privileges);
}
