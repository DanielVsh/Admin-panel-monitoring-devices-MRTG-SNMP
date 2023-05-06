package danielvishnievskyi.bachelorproject.exception.device;

/**
 * An exception that is thrown when a not found is made to the Device entity.
 *
 * @author [Daniel Vishnievskyi].
 */
public class DeviceNotFoundException extends RuntimeException {

  /**
   * Constructs a new DeviceNotFoundException with the specified detail message.
   *
   * @param message the detail message
   */
  public DeviceNotFoundException(String message) {
    super(message);
  }
}
