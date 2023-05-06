package danielvishnievskyi.bachelorproject.exception;

import java.time.LocalDateTime;

/**
 * Represents an error response returned by the API in case of an error.
 * This is a record class that encapsulates the details of an error, including the timestamp when the error occurred,
 * the HTTP status code, a brief error message, a more detailed error message, and the path that triggered the error.
 * This class is designed to be immutable and its fields are accessible via their respective accessor methods.
 *
 * @param timestamp The date and time when the error occurred.
 * @param status    The HTTP status code of the error response.
 * @param error     A brief error message that summarizes the error.
 * @param message   A more detailed error message that provides additional information about the error.
 * @param path      The URI path that triggered the error.
 * @author [Daniel Vishnievskyi].
 */
public record ErrorResponse(
  LocalDateTime timestamp,
  int status,
  String error,
  String message,
  String path
) {
}
