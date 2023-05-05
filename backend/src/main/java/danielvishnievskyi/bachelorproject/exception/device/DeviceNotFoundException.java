package danielvishnievskyi.bachelorproject.exception.device;

public class DeviceNotFoundException extends RuntimeException{
  public DeviceNotFoundException(String message) {
    super(message);
  }
}
