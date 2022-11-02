package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.repositories.DeviceRepo;
import danielvishnievskyi.bachelorproject.services.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DeviceImpl {
  private final DeviceRepo deviceRepo;

  public Collection<Device> getDevices() {
    return deviceRepo.findAll();
  }

  public void save(Device device) {
    deviceRepo.save(device);
  }

  public Collection<Device> getDevicesByIds(Collection<Long> ids) {
    return ids.stream()
      .map(this::getById)
      .collect(Collectors.toList());
  }

  public void delete(Long id) {
    deviceRepo.delete(getById(id));
  }

  public Device getById(Long id) {
    try {
      return deviceRepo.findById(id).orElseThrow(NotFoundException::new);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void deleteAllById(Collection<Long> ids) {
    deviceRepo.deleteAllById(ids);
  }

  public Device getByName(String name) {
    try {
      return deviceRepo.getByName(name).orElseThrow(NotFoundException::new);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
