package danielvishnievskyi.bachelorproject.exception.device;

/**
 * This exception is thrown when a bad request is made for a Device resource.
 *
 * @author [Daniel Vishnievskyi].
 */
public class DeviceBadRequestException extends RuntimeException {

  /**
   * Constructs a new DeviceBadRequestException with the specified detail message.
   *
   * @param message the detail message.
   */
  public DeviceBadRequestException(String message) {
    super(message);
  }
}
