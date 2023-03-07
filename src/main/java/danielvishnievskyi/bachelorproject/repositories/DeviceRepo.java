package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.Device;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface DeviceRepo extends JpaRepository<Device, Long>,
  JpaSpecificationExecutor<Device>{
  Optional<Device> getByName(String name);

//  @Query(value = """
//    SELECT new danielvishnievskyi.bachelorproject.dto.audit.AuditDeviceDTO(
//      la.id,
//      la.buildingId,
//      la.rev,
//      la.revType,
//      la.name,
//      la.snmp,
//      la.ipAddress,
//      la.switchMap,
//      la.uptime,
//      er.timestamp,
//      er.username
//    )
//    FROM AuditDevice la
//    JOIN EnversRev er ON la.rev = er.id
//  """)
//  List<AuditLocationDTO> findLastChanges(Pageable pageable);
}
