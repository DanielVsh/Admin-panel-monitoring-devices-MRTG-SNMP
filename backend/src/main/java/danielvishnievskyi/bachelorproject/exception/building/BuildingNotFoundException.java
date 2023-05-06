package danielvishnievskyi.bachelorproject.exception.building;

/**
 * An exception that is thrown when a not found is made to the Building entity.
 *
 * @author [Daniel Vishnievskyi].
 */
public class BuildingNotFoundException extends RuntimeException {

  /**
   * Constructs a new BuildingNotFoundException with the specified detail message.
   *
   * @param message the detail message.
   */
  public BuildingNotFoundException(String message) {
    super(message);
  }
}
