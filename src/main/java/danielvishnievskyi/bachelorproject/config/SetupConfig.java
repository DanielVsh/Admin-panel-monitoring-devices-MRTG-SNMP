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
    BuildingsLocationService buildingLocationService,
    BuildingService buildingService,
    DeviceService deviceService
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


      Building j9 = buildingService.createIfNotFound("J9");
      Building j5 = buildingService.createIfNotFound("J5");
      Building j7 = buildingService.createIfNotFound("J7");
      BuildingsLocation jLoc = buildingLocationService.createIfNotFound("Jedlikova");
      buildingLocationService.joinBuildingsToLocation(Set.of(j9, j5, j7), jLoc.getId());
      Device mainPc = new Device("main", "172.16.254.1", false);
      deviceService.save(mainPc);
      buildingService.joinDevicesToBuilding(Set.of(mainPc), j5.getId());

      Building u1 = buildingService.createIfNotFound("Urbanka1");
      Building u2 = buildingService.createIfNotFound("Urbanka2");
      Building u3 = buildingService.createIfNotFound("Urbanka3");
      BuildingsLocation urbanka = buildingLocationService.createIfNotFound("Urbanka");
      buildingLocationService.joinBuildingsToLocation(Set.of(u1, u2, u3), urbanka.getId());
      Device uPc = new Device("ur", "123.123.12.2", true);
      Device u2Pc = new Device("ua", "133.2.2.2", false);
      deviceService.save(uPc);
      deviceService.save(u2Pc);
      buildingService.joinDevicesToBuilding(Set.of(uPc, u2Pc), u1.getId());

      Building tuke1 = buildingService.createIfNotFound("tuke1");
      Building tuke2 = buildingService.createIfNotFound("tuke2");
      Building tuke3 = buildingService.createIfNotFound("tuke3");
      Building tuke4 = buildingService.createIfNotFound("tuke4");
      Building tuke5 = buildingService.createIfNotFound("tuke5");
      Building tuke6 = buildingService.createIfNotFound("tuke6");
      BuildingsLocation tuke = buildingLocationService.createIfNotFound("Tuke");
      buildingLocationService.joinBuildingsToLocation(Set.of(tuke1, tuke2, tuke3, tuke4, tuke5, tuke6), tuke.getId());
      Device pc1 = new Device("pc1", "12.152.112.2", true);
      Device pc2 = new Device("pc2", "213.152.12.2", true);
      Device pc3 = new Device("pc3", "123.23.12.28", true);
      Device pc4 = new Device("pc4", "112.123.271.2", true);
      Device pc5 = new Device("pc5", "215.123.12.28", true);
      Device pc6 = new Device("pc6", "133.52.25.52", false);
      Device pc7 = new Device("pc7", "228.52.25.52", false);
      deviceService.save(pc1);
      deviceService.save(pc2);
      deviceService.save(pc3);
      deviceService.save(pc4);
      deviceService.save(pc5);
      deviceService.save(pc6);
      buildingService.joinDevicesToBuilding(Set.of(pc1, pc2), tuke1.getId());
      buildingService.joinDevicesToBuilding(Set.of(pc3), tuke2.getId());
      buildingService.joinDevicesToBuilding(Set.of(pc4), tuke3.getId());
      buildingService.joinDevicesToBuilding(Set.of(pc5), tuke4.getId());
      buildingService.joinDevicesToBuilding(Set.of(pc6), tuke5.getId());
      buildingService.joinDevicesToBuilding(Set.of(pc7), tuke5.getId());

    };
  }
}
