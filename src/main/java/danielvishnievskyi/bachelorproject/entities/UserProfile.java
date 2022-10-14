package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@Transactional
@NoArgsConstructor
public class UserProfile implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false)
  @JsonIgnore
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JsonIgnore
  @JoinTable(
    name = "users_roles",
    joinColumns = @JoinColumn(
      name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(
      name = "role_id", referencedColumnName = "id"))
  @ToString.Exclude
  private Collection<Role> roles;

  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;
  private boolean isEnabled;

  public UserProfile(String username,
                     String password,
                     Collection<Role> roles,
                     boolean isAccountNonExpired,
                     boolean isAccountNonLocked,
                     boolean isCredentialsNonExpired,
                     boolean isEnabled) {
    this.username = username;
    this.password = password;
    this.roles = roles;
    this.isAccountNonExpired = isAccountNonExpired;
    this.isAccountNonLocked = isAccountNonLocked;
    this.isCredentialsNonExpired = isCredentialsNonExpired;
    this.isEnabled = isEnabled;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    var privileges = roles.stream()
      .map(Role::getName)
      .collect(Collectors.toSet());
    var collection = roles.stream()
      .flatMap(role -> role.getPrivileges().stream()
        .map(Privilege::getName))
      .collect(Collectors.toSet());
    privileges.addAll(collection);
    return privileges.stream()
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toSet());
  }
}
