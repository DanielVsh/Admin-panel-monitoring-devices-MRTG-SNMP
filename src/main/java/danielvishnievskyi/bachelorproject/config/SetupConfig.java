package danielvishnievskyi.bachelorproject.config;

import danielvishnievskyi.bachelorproject.entities.*;
import danielvishnievskyi.bachelorproject.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class SetupConfig {

  @Bean
  public CommandLineRunner commandLineRunner(
    AdminProfileService userProfileService,
    RoleService roleService,
    PrivilegeService privilegeService,
    LocationService locationService,
    BuildingService buildingService,
    DeviceService deviceService,
    DeviceBuildingLocationService devBuildLocService
  ) {
    return args -> {
      Privilege adminWritePrivilege = privilegeService.createIfNotFound("admin:write");
      Privilege adminViewPrivilege = privilegeService.createIfNotFound("admin:view");
      Role roleAdmin = roleService.createIfNotFound("ADMIN", Set.of(adminViewPrivilege));
      Role superAdmin = roleService.createIfNotFound("SUPER_ADMIN", Set.of(adminViewPrivilege, adminWritePrivilege));

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



      var loc1 = locationService.createIfNotFound("Jedlikova");
      var build1 = buildingService.createIfNotFound("Jedlikova 9");
      var pc1 = new Device("PC1", "123.123.123.123", false, "PRIVATE");
      deviceService.save(pc1);
      devBuildLocService.joinBuildingsToLocation(Set.of(build1.getId()), loc1.getId());
      devBuildLocService.joinDevicesToBuilding(Set.of(pc1.getId()), build1.getId());

    };
  }
}
