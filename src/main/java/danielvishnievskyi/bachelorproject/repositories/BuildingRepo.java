package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.Building;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface BuildingRepo extends JpaRepository<Building, Long>,
  JpaSpecificationExecutor<Building>{
  Optional<Building> getByName(String name);

//  @Query(value = """
//    SELECT new danielvishnievskyi.bachelorproject.dto.audit.AuditBuildingDTO(
//      la.id,
//      la.rev,
//      la.revtype,
//      la.name,
//      la.locationId,
//      er.timestamp,
//      er.username
//    )
//    FROM AuditBuilding la
//    JOIN EnversRev er ON la.rev = er.id
//  """)
//  List<AuditBuildingDTO> findLastChanges(Pageable pageable);
}
