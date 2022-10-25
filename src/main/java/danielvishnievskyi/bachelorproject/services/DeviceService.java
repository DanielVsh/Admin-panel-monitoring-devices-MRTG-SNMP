package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.implementations.DeviceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DeviceService {
  private final DeviceImpl deviceImpl;

  public Collection<Device> getDevices() {
    return deviceImpl.getDevices();
  }

  public Collection<Device> getByBuilding(Long buildingId) {
    return deviceImpl.getByBuilding(buildingId);
  }

  public void deleteFromBuilding(Long id) {
    deviceImpl.deleteFromBuilding(id);
  }

  public void save(Device device) {
    deviceImpl.save(device);
  }

  public void delete(Long id) {
    deviceImpl.delete(id);
  }

  public Device getById(Long id) {
    return deviceImpl.getById(id);
  }

  public Device getByName(String name) {
    return deviceImpl.getByName(name);
  }
}
