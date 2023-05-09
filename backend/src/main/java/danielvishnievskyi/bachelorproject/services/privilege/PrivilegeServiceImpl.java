package danielvishnievskyi.bachelorproject.services.privilege;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.repositories.PrivilegeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {
  private final PrivilegeRepo privilegeRepo;

  @Transactional
  @Override
  public Privilege createIfNotFound(String name) {
    return privilegeRepo.getByName(name.toUpperCase()).orElseGet(() -> {
      Privilege privilege = new Privilege(name.toUpperCase());
      privilegeRepo.save(privilege);
      return privilege;
    });
  }
}
