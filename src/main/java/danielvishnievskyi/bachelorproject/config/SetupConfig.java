package danielvishnievskyi.bachelorproject.config;

import danielvishnievskyi.bachelorproject.entities.*;
import danielvishnievskyi.bachelorproject.enums.SearchOperation;
import danielvishnievskyi.bachelorproject.repositories.BuildingRepo;
import danielvishnievskyi.bachelorproject.repositories.LocationRepo;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import danielvishnievskyi.bachelorproject.repositories.specifications.LocationSpecification;
import danielvishnievskyi.bachelorproject.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


      var loc  = IntStream.rangeClosed(1, 300000)
        .mapToObj(value -> new Location("Location " + (1000-value))).toList();
      locationRepo.saveAll(loc);
      var build  = IntStream.rangeClosed(1, 2000)
        .mapToObj(value -> new Building(
          "Building " + (1000-value),
          loc.get(value))).toList();
      buildingRepo.saveAll(build);
    };
  }
}
