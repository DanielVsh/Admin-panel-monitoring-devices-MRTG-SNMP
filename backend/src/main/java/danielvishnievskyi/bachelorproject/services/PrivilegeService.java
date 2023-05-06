package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.repositories.PrivilegeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * A service class that provides operations related to privileges.
 *
 * @author [Daniel Vishnievskyi].
 */
@Service
@RequiredArgsConstructor
public class PrivilegeService {
  private final PrivilegeRepo privilegeRepo;

  /**
   * Creates a new privilege if it does not already exist.
   *
   * @param name the name of the privilege to create or retrieve
   * @return the newly created or retrieved privilege
   */
  @Transactional
  public Privilege createIfNotFound(String name) {
    return privilegeRepo.getByName(name.toUpperCase()).orElseGet(() -> {
      Privilege privilege = new Privilege(name.toUpperCase());
      privilegeRepo.save(privilege);
      return privilege;
    });
  }
}
