package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.AdminProfile;
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

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

/**
 * Implementation of the UserDetailsService for the AdminProfile entity, providing authentication and authorization
 * functionality for administrators. This class implements the loadUserByUsername method of the UserDetailsService
 * interface, which returns a UserDetails object containing the username, password, and authorities (roles and
 * privileges) of the administrator. This class also provides methods for retrieving, saving, and deleting admin profiles
 * from the database.
 *
 * @author [Daniel Vishnievskyi].
 */
@Component
@RequiredArgsConstructor
public class AdminProfileImpl implements UserDetailsService {
  private final AdminProfileRepo userRepo;
  private final PasswordEncoder passwordEncoder;

  /**
   * Retrieves an admin profile from the database by its username.
   *
   * @param username the username of the admin profile to retrieve
   * @return the admin profile with the specified username
   * @throws RuntimeException if the admin profile is not found in the database
   */
  public AdminProfile getByUsername(String username) {
    try {
      return userRepo.getByUsername(username).orElseThrow(NotFoundException::new);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Saves an admin profile to the database.
   *
   * @param userProfile the admin profile to save
   */
  public void save(AdminProfile userProfile) {
    userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
    userRepo.save(userProfile);
  }

  /**
   * Deletes an admin profile from the database.
   *
   * @param userProfile the admin profile to delete
   */
  public void delete(AdminProfile userProfile) {
    userRepo.delete(userProfile);
  }

  /**
   * Loads an admin profile from the database by its username, and returns a UserDetails object containing the username,
   * password, and authorities (roles and privileges) of the admin profile.
   *
   * @param username the username of the admin profile to load
   * @return a UserDetails object containing the username, password, and authorities of the admin profile
   */
  @Override
  public UserDetails loadUserByUsername(String username) {
    AdminProfile userProfile = getByUsername(username);
    return new User(
      userProfile.getUsername(),
      userProfile.getPassword(),
      getAuthorities(userProfile.getRoles())
    );
  }

  /**
   * Returns a set of GrantedAuthority objects representing the roles and privileges of the specified set of roles.
   *
   * @param roles the set of roles for which to retrieve authorities
   * @return a set of GrantedAuthority objects representing the roles and privileges of the specified set of roles
   */
  private Set<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
    return getGrantedAuthorities(getPrivileges(roles));
  }

  /**
   * Returns a set of GrantedAuthority objects representing the specified set of privileges.
   *
   * @param privileges the set of privileges for which to retrieve authorities
   * @return a set of GrantedAuthority objects representing the specified set of privileges
   */
  private Set<? extends GrantedAuthority> getGrantedAuthorities(Set<String> privileges) {
    return privileges.stream()
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toSet());
  }

  /**
   * Returns a set of strings representing the privileges of the specified set of roles.
   *
   * @param roles the set of roles for which to retrieve privileges
   * @return a set of strings representing the privileges of the specified set of roles
   */
  private Set<String> getPrivileges(Set<Role> roles) {
    return roles.stream()
      .map(Role::getName)
      .collect(Collectors.toSet());
  }
}
