package danielvishnievskyi.bachelorproject.services.privilege;

import danielvishnievskyi.bachelorproject.entities.Privilege;

/**
 * A service class that provides operations related to privileges.
 *
 * @author [Daniel Vishnievskyi].
 */
public interface PrivilegeService {

  /**
   * Creates a new privilege if it does not already exist.
   *
   * @param name the name of the privilege to create or retrieve
   * @return the newly created or retrieved privilege
   */
  Privilege createIfNotFound(String name);
}
