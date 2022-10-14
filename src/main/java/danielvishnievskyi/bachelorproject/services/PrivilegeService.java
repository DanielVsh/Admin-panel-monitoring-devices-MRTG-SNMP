package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.repositories.PrivilegeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PrivilegeService {
  private final PrivilegeRepo privilegeRepo;

  public void savePrivilege(Privilege privilege) {
    privilegeRepo.save(privilege);
  }

  @Transactional
  public Privilege createPrivilegeIfNotFound(String name) {
    Privilege privilege = privilegeRepo.findPrivilegeByName(name);
    if (privilege == null) {
      privilege = new Privilege(name);
      savePrivilege(privilege);
    }
    return privilege;
  }
}
