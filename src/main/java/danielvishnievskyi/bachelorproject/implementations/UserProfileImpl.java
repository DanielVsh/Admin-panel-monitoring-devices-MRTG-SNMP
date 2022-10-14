package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.UserProfile;
import danielvishnievskyi.bachelorproject.repositories.UserProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileImpl implements UserDetailsService {
  private final UserProfileRepo userRepo;

  public UserProfile getUserByUsername(String username) {
    return userRepo.findUserProfileByUsername(username);
  }

  public void saveUser(UserProfile userProfile) {
    userRepo.save(userProfile);
  }

  public void deleteUser(UserProfile userProfile) {
    userRepo.delete(userProfile);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserProfile userProfile = getUserByUsername(username);
    if (userProfile == null) {
      throw new UsernameNotFoundException(String.format("User %s is not found", username));
    }
    return new User(
      userProfile.getUsername(),
      userProfile.getPassword(),
      userProfile.isEnabled(),
      userProfile.isAccountNonExpired(),
      userProfile.isCredentialsNonExpired(),
      userProfile.isAccountNonLocked(),
      userProfile.getAuthorities()
    );
  }
}
