package danielvishnievskyi.bachelorproject.config;

import danielvishnievskyi.bachelorproject.entities.*;
import danielvishnievskyi.bachelorproject.enums.SearchOperation;
import danielvishnievskyi.bachelorproject.repositories.BuildingRepo;
import danielvishnievskyi.bachelorproject.repositories.DeviceRepo;
import danielvishnievskyi.bachelorproject.repositories.LocationRepo;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.LocationSpecification;
import danielvishnievskyi.bachelorproject.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static danielvishnievskyi.bachelorproject.enums.SearchOperation.*;

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

//        Location loc1 = new Location("Loc1");
//        Location loc2 = new Location("Loc2");
//        Location loc3 = new Location("Loc3");
//        Location loc4 = new Location("Loc4");
//        Location loc5 = new Location("Loc5");
//        locationRepo.saveAll(Set.of(loc1, loc2, loc3, loc4, loc5));
//        Building build1 = new Building("Build1", loc1);
//        Building build2 = new Building("Build2", loc1);
//        Building build3 = new Building("Build3", loc2);
//        Building build4 = new Building("Build4", loc3);
//        Building build5 = new Building("Build5", loc4);
//        buildingRepo.saveAll(Set.of(build1, build2, build3, build4, build5));

//      var loc  = IntStream.rangeClosed(1, 3000)
//        .mapToObj(value -> new Location("Location " + (3000+value))).toList();
//      locationRepo.saveAll(loc);
//      var build  = IntStream.rangeClosed(1, 2000)
//        .mapToObj(value -> new Building(
//          "Building " + (2000+value),
//          loc.get(value))).toList();
//      buildingRepo.saveAll(build);
//      var device  = IntStream.rangeClosed(1, 1500)
//        .mapToObj(value -> new Device(
//          "Device " + (1500+value),
//          build.get(value),
//          "123.123.123.123" + value,
//          false)).toList();
//      deviceRepo.saveAll(device);
    };
  }
}
