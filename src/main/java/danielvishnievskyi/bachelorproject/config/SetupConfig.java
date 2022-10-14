package danielvishnievskyi.bachelorproject.config;

import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.entities.Role;
import danielvishnievskyi.bachelorproject.entities.UserProfile;
import danielvishnievskyi.bachelorproject.services.PrivilegeService;
import danielvishnievskyi.bachelorproject.services.RoleService;
import danielvishnievskyi.bachelorproject.services.UserProfileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SetupConfig {

  @Bean
  public CommandLineRunner commandLineRunner(
    UserProfileService userProfileService,
    RoleService roleService,
    PrivilegeService privilegeService
  ) {
    return args -> {
      Privilege userWritePrivilege = privilegeService.createPrivilegeIfNotFound("user:write");
      Privilege adminWritePrivilege = privilegeService.createPrivilegeIfNotFound("admin:write");
      Role roleUser = roleService.createRoleIfNotFound("USER", Set.of(userWritePrivilege));
      Role roleAdmin = roleService.createRoleIfNotFound("ADMIN", Set.of(userWritePrivilege, adminWritePrivilege));

      UserProfile user = new UserProfile(
        "user",
        "user",
        Set.of(roleUser),
        true,
        true,
        true,
        true);


      UserProfile admin = new UserProfile(
        "admin",
        "admin",
        Set.of(roleAdmin),
        true,
        true,
        true,
        true);

      userProfileService.saveUser(user);
      userProfileService.saveUser(admin);
    };
  }
}
