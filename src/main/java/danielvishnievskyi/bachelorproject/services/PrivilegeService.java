package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.implementations.PrivilegeImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrivilegeService {
  private final PrivilegeImpl privilegeImpl;

  public void save(Privilege privilege) {
    privilegeImpl.save(privilege);
  }

  public void delete(Privilege privilege) {
    privilegeImpl.delete(privilege);
  }

  public Privilege createIfNotFound(String name) {
    return privilegeImpl.createIfNotFound(name);
  }
}
