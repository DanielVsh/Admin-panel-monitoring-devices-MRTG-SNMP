package danielvishnievskyi.bachelorproject.exception.location;

/**
 * This exception is thrown when a bad request is made for a Location resource.
 *
 * @author [Daniel Vishnievskyi].
 */
public class LocationBadRequestException extends RuntimeException {

  /**
   * Constructs a new LocationBadRequestException with the specified detail message.
   *
   * @param message the detail message
   */
  public LocationBadRequestException(String message) {
    super(message);
  }
}
