package danielvishnievskyi.bachelorproject.exception.location;

public class LocationNotFoundException extends RuntimeException{
  public LocationNotFoundException(String message) {
    super(message);
  }
}
