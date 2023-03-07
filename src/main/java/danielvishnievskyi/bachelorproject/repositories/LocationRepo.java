package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.Location;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface LocationRepo extends JpaRepository<Location, Long>,
  JpaSpecificationExecutor<Location>{
  Optional<Location> getByName(String name);

//  @Query(value = """
//    SELECT new danielvishnievskyi.bachelorproject.dto.audit.AuditLocationDTO(
//      la.id,
//      la.rev,
//      la.revtype,
//      la.name,
//      er.timestamp,
//      er.username
//    )
//    FROM AuditLocation la
//    JOIN EnversRev er ON la.rev = er.id
//  """)
//  List<AuditLocationDTO> findLastChanges(Pageable pageable);
}
