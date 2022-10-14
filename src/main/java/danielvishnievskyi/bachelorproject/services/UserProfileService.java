package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.UserProfile;
import danielvishnievskyi.bachelorproject.implementations.UserProfileImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
  private final UserProfileImpl userImpl;
  private final PasswordEncoder passwordEncoder;

  public UserProfile getUserByUsername(String username) {
    return userImpl.getUserByUsername(username);
  }

  public void saveUser(UserProfile userProfile) {
    userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
    userImpl.saveUser(userProfile);
  }

  public void deleteUser(UserProfile userProfile) {
    userImpl.deleteUser(userProfile);
  }
}
