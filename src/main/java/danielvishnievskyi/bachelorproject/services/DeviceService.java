package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.repositories.DeviceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService{
  private final DeviceRepo deviceRepo;

  public Page<Device> findAll(Pageable page) {
    return deviceRepo.findAll(page);
  }

  public Page<Device> findAll(Specification<Device> specification, Pageable page) {
    return deviceRepo.findAll(specification, page);
  }

  public Collection<Device> findAllById(Collection<Long> ids) {
    return deviceRepo.findAllById(ids);
  }

  public void save(Device device) {
    deviceRepo.save(device);
  }

  public void deleteAllById(Collection<Long> ids) {
    deviceRepo.deleteAllById(ids);
  }

  public void deleteById(Long id) {
    deviceRepo.deleteById(id);
  }

  public Optional<Device> findById(Long id) {
    return deviceRepo.findById(id);
  }

  public Optional<Device> findByName(String name) {
    return deviceRepo.findByName(name);
  }
}
