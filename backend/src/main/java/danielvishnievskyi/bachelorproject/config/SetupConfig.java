package danielvishnievskyi.bachelorproject.config;

import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import danielvishnievskyi.bachelorproject.entities.Privilege;
import danielvishnievskyi.bachelorproject.entities.Role;
import danielvishnievskyi.bachelorproject.services.admin.AdminProfileServiceImpl;
import danielvishnievskyi.bachelorproject.services.privilege.PrivilegeService;
import danielvishnievskyi.bachelorproject.services.role.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class SetupConfig {

  @Bean
  public CommandLineRunner commandLineRunner(
    AdminProfileServiceImpl userProfileService,
    RoleService roleService,
    PrivilegeService privilegeService
  ) {
    return args -> {
      Privilege adminWritePrivilege = privilegeService.createIfNotFound("admin:write");
      Privilege adminViewPrivilege = privilegeService.createIfNotFound("admin:view");
      Privilege adminDeletePrivilege = privilegeService.createIfNotFound("admin:delete");

      Role roleAdmin = roleService.createIfNotFound("ADMIN_VIEW", Set.of(adminViewPrivilege));
      Role roleAdminModerator = roleService.createIfNotFound("ADMIN_WRITE", Set.of(adminWritePrivilege, adminViewPrivilege));
      Role superAdmin = roleService.createIfNotFound("SUPER_ADMIN", Set.of(adminDeletePrivilege, adminWritePrivilege, adminViewPrivilege));

      AdminProfile admin = new AdminProfile(
        "admin",
        "daniel",
        "vish",
        "admin",
        Set.of(roleAdmin));
      userProfileService.save(admin);
      AdminProfile super_admin = new AdminProfile(
        "boss",
        "super",
        "boss",
        "boss",
        Set.of(superAdmin));
      userProfileService.save(super_admin);
      AdminProfile admin_write = new AdminProfile(
        "writer",
        "super",
        "boss",
        "writer",
        Set.of(roleAdminModerator));
      userProfileService.save(admin_write);
    };
  }
}
