package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.repositories.PrivilegeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class PrivilegeImpl {
  private final PrivilegeRepo privilegeRepo;

  public void save(Privilege privilege) {
    privilegeRepo.save(privilege);
  }

  public void delete(Privilege privilege) {
    privilegeRepo.delete(privilege);
  }

  @Transactional
  public Privilege createIfNotFound(String name) {
    return privilegeRepo.getByName(name.toUpperCase()).orElseGet(() -> {
      Privilege privilege = new Privilege(name.toUpperCase());
      save(privilege);
      return privilege;
    });

  }
}
