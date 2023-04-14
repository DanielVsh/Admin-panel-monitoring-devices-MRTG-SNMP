package danielvishnievskyi.bachelorproject.config;

import danielvishnievskyi.bachelorproject.entities.*;
import danielvishnievskyi.bachelorproject.repositories.BuildingRepo;
import danielvishnievskyi.bachelorproject.repositories.DeviceRepo;
import danielvishnievskyi.bachelorproject.repositories.LocationRepo;
import danielvishnievskyi.bachelorproject.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class SetupConfig {

  @Bean
  public CommandLineRunner commandLineRunner(
    AdminProfileService userProfileService,
    RoleService roleService,
    PrivilegeService privilegeService,
    LocationRepo locationRepo,
    BuildingRepo buildingRepo,
    DeviceRepo deviceRepo,
    LocationService locationService,
    BuildingService buildingService,
    DeviceService deviceService
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

      Location loc1 = new Location("Loc1");
      Location loc2 = new Location("Loc2");
      Location loc3 = new Location("Loc3");
      Location loc4 = new Location("Loc4");
      Location loc5 = new Location("Loc5");
      locationRepo.saveAll(Set.of(loc1, loc2, loc3, loc4, loc5));
      Building build1 = new Building("Build1", loc1);
      Building build2 = new Building("Build2", loc1);
      Building build3 = new Building("Build3", loc2);
      Building build4 = new Building("Build4", loc3);
      Building build5 = new Building("Build5", loc4);
      buildingRepo.saveAll(Set.of(build1, build2, build3, build4, build5));

      Device device = new Device("Dev", build1, "147.232.205.203", true);
      Device device1 = new Device("Dev1", build1, "147.232.205.204", true);
      Device device2 = new Device("Dev2", build2, "147.232.205.205", true);
      deviceRepo.saveAll(List.of(device, device1, device2));

//      var loc  = IntStream.rangeClosed(1, 2000)
//        .mapToObj(value -> new Location("Location " + (3000+value))).toList();
//      locationRepo.saveAll(loc);
//      var build  = IntStream.rangeClosed(1, 1000)
//        .mapToObj(value -> new Building(
//          "Building " + (2000+value),
//          loc.get(value))).toList();
//      buildingRepo.saveAll(build);
//      var device  = IntStream.rangeClosed(1, 500)
//        .mapToObj(value -> new Device(
//          "Device " + (1500+value),
//          build.get(value),
//          "123.123.123.123" + value,
//          false)).toList();
//      deviceRepo.saveAll(device);
//
//
//      IntStream.rangeClosed(1,300).forEach((i) -> {
//        device.get(0).setName("TEST"+i);
//        deviceRepo.save(device.get(0));
//      });
    };
  }
}
