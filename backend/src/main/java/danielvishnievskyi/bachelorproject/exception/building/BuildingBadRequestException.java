package danielvishnievskyi.bachelorproject.exception.building;

/**
 * An exception that is thrown when a bad request is made to the Building entity.
 *
 * @author [Daniel Vishnievskyi].
 */
public class BuildingBadRequestException extends RuntimeException {

  /**
   * Constructs a new BuildingBadRequestException with the specified detail message.
   *
   * @param message the detail message
   */
  public BuildingBadRequestException(String message) {
    super(message);
  }
}
