package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.implementations.DeviceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {
  private final DeviceImpl deviceImpl;
  private final BuildingService buildingService;

  public Collection<Device> getAll() {
    return deviceImpl.getAll();
  }

  public Collection<Device> getManyByIds(Collection<Long> ids) {
    return deviceImpl.getManyByIds(ids);
  }

  public void save(Device device) {
    deviceImpl.save(device);
  }

  public void deleteManyByIds(Collection<Long> ids) {
    deviceImpl.deleteManyById(ids);
  }

  public void delete(Long id) {
    deviceImpl.delete(id);
  }

  public Optional<Device> getById(Long id) {
    return deviceImpl.getById(id);
  }

  public Optional<Device> getByName(String name) {
    return deviceImpl.getByName(name);
  }
}
