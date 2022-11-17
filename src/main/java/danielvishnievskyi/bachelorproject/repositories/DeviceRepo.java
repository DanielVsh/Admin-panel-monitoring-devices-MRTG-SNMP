package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DeviceRepo extends JpaRepository<Device, Long>,
  JpaSpecificationExecutor<Device> {
  Optional<Device> getByName(String name);
}
