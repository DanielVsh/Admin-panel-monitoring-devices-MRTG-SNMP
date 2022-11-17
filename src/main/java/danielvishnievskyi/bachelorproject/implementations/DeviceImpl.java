package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.repositories.DeviceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DeviceImpl {
  private final DeviceRepo deviceRepo;

  public Page<Device> findAll(Pageable page) {
    return deviceRepo.findAll(page);
  }

  public Page<Device> findAll(Specification<Device> specification, Pageable page) {
    return deviceRepo.findAll(specification, page);
  }

  public void save(Device device) {
    deviceRepo.save(device);
  }

  public Collection<Device> getManyByIds(Collection<Long> ids) {
    return ids.stream()
      .map(value -> getById(value).orElseThrow())
      .collect(Collectors.toList());
  }

  public void delete(Long id) {
    deviceRepo.deleteById(id);
  }

  public Optional<Device> getById(Long id) {
      return deviceRepo.findById(id);
  }

  public void deleteManyById(Collection<Long> ids) {
    deviceRepo.deleteAllById(ids);
  }

  public Optional<Device> getByName(String name) {
      return deviceRepo.getByName(name);
  }
}
