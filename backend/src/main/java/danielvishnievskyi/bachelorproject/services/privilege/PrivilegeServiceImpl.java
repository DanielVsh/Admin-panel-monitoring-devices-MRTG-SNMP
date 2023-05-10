package danielvishnievskyi.bachelorproject.services.privilege;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.repositories.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {
  private final PrivilegeRepository privilegeRepository;

  @Transactional
  @Override
  public Privilege createIfNotFound(String name) {
    return privilegeRepository.getByName(name.toUpperCase()).orElseGet(() -> {
      Privilege privilege = new Privilege(name.toUpperCase());
      privilegeRepository.save(privilege);
      return privilege;
    });
  }
}
