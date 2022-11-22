package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import danielvishnievskyi.bachelorproject.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface DeviceRepo extends JpaRepository<Device, Long>,
  JpaSpecificationExecutor<Device>,
  RevisionRepository<Device, Long, Long> {
  Optional<Device> getByName(String name);
}
