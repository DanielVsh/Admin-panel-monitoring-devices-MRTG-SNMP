package danielvishnievskyi.bachelorproject.exception.location;


/**
 * An exception that is thrown when a not found is made to the Location entity.
 *
 * @author [Daniel Vishnievskyi].
 */
public class LocationNotFoundException extends RuntimeException {

  /**
   * Constructs a new LocationNotFoundException with the specified detail message.
   *
   * @param message the detail message
   */
  public LocationNotFoundException(String message) {
    super(message);
  }
}
