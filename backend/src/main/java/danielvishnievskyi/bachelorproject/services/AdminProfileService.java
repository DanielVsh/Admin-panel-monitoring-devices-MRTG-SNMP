package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import danielvishnievskyi.bachelorproject.implementations.AdminProfileImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProfileService {
  private final AdminProfileImpl userImpl;

  public AdminProfile getByUsername(String username) {
    return userImpl.getByUsername(username);
  }

  public void save(AdminProfile userProfile) {
    userImpl.save(userProfile);
  }

  public void delete(AdminProfile userProfile) {
    userImpl.delete(userProfile);
  }
}
