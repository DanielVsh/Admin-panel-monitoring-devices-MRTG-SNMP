package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.entities.Role;
import danielvishnievskyi.bachelorproject.repositories.AdminProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.data.crossstore.ChangeSetPersister.*;

@Component
@RequiredArgsConstructor
public class AdminProfileImpl implements UserDetailsService {
  private final AdminProfileRepo userRepo;
  private final PasswordEncoder passwordEncoder;

  public AdminProfile getByUsername(String username){
    try {
      return userRepo.getByUsername(username).orElseThrow(NotFoundException::new);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void save(AdminProfile userProfile) {
    userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
    userRepo.save(userProfile);
  }

  public void delete(AdminProfile userProfile) {
    userRepo.delete(userProfile);
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    AdminProfile userProfile = getByUsername(username);
    return new User(
      userProfile.getUsername(),
      userProfile.getPassword(),
      getAuthorities(userProfile.getRoles())
    );
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
    return getGrantedAuthorities(getPrivileges(roles));
  }

  private Collection<? extends GrantedAuthority> getGrantedAuthorities(Collection<String> privileges) {
    return privileges.stream()
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toSet());
  }

  private Collection<String> getPrivileges(Collection<Role> roles) {
    var privileges = roles.stream()
      .map(Role::getName)
      .collect(Collectors.toSet());

    var collection = roles.stream()
      .flatMap(role -> role.getPrivileges().stream()
        .map(Privilege::getName))
      .collect(Collectors.toSet());

    privileges.addAll(collection);

    return privileges;
  }
}
