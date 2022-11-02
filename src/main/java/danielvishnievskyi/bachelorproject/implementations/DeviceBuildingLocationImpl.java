package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.services.BuildingService;
import danielvishnievskyi.bachelorproject.services.DeviceService;
import danielvishnievskyi.bachelorproject.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class DeviceBuildingLocationImpl {
  private final LocationService locationService;
  private final DeviceService deviceService;
  private final BuildingService buildingService;


  public void joinDevicesToBuilding(Collection<Long> devicesIds, Long buildingId) {
    var build = buildingService.getById(buildingId);
    build.getDevices().addAll(deviceService.getDevicesByIds(devicesIds));
    build.setDevices(build.getDevices());
    buildingService.save(build);
  }

  public void deleteDevicesFromBuilding(Collection<Long> ids) {
    try {
      buildingService.save(buildingService.getBuildings().stream()
        .filter(buildLoc -> buildLoc.getDevices().removeAll(deviceService.getDevicesByIds(ids)))
        .findFirst().orElseThrow(ChangeSetPersister.NotFoundException::new));
      deviceService.deleteAllByIds(ids);
    } catch (ChangeSetPersister.NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public Collection<Device> getDevicesByBuilding(Long buildingId) {
    return buildingService.getById(buildingId).getDevices();
  }

  public void deleteBuildingsFromLocation(Collection<Long> ids) {
    try {
      locationService.save(locationService.getBuildingsLocations().stream()
        .filter(buildLoc -> buildLoc.getBuildings().removeAll(buildingService.getBuildingsByIds(ids)))
        .findFirst().orElseThrow(ChangeSetPersister.NotFoundException::new));
    } catch (ChangeSetPersister.NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void joinBuildingsToLocation(Collection<Long> buildingsIds, Long locationId) {
    var location = locationService.getById(locationId);
    location.getBuildings().addAll(buildingService.getBuildingsByIds(buildingsIds));
    location.setBuildings(location.getBuildings());
    locationService.save(location);
  }
}
