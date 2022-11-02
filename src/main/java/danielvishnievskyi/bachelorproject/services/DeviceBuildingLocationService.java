package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.implementations.DeviceBuildingLocationImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DeviceBuildingLocationService {
  private final DeviceBuildingLocationImpl deviceBuildingLocation;

  public void joinDevicesToBuilding(Collection<Long> devicesIds, Long buildingId) {
    deviceBuildingLocation.joinDevicesToBuilding(devicesIds, buildingId);
  }

  public void deleteDevicesFromBuilding(Collection<Long> ids) {
    deviceBuildingLocation.deleteDevicesFromBuilding(ids);
  }

  public Collection<Device> getDevicesByBuilding(Long buildingId) {
    return deviceBuildingLocation.getDevicesByBuilding(buildingId);
  }

  public void deleteBuildingsFromLocation(Collection<Long> ids) {
    deviceBuildingLocation.deleteBuildingsFromLocation(ids);
  }

  public void joinBuildingsToLocation(Collection<Long> buildingsIds, Long locationId) {
    deviceBuildingLocation.joinBuildingsToLocation(buildingsIds, locationId);
  }
}
