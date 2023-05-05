package danielvishnievskyi.bachelorproject.exception;

import danielvishnievskyi.bachelorproject.exception.building.BuildingBadRequestException;
import danielvishnievskyi.bachelorproject.exception.building.BuildingNotFoundException;
import danielvishnievskyi.bachelorproject.exception.device.DeviceBadRequestException;
import danielvishnievskyi.bachelorproject.exception.device.DeviceNotFoundException;
import danielvishnievskyi.bachelorproject.exception.location.LocationBadRequestException;
import danielvishnievskyi.bachelorproject.exception.location.LocationNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ErrorHandlerController {

  @ExceptionHandler({
    LocationNotFoundException.class,
    BuildingNotFoundException.class,
    DeviceNotFoundException.class
  })
  @ResponseStatus(NOT_FOUND)
  public ErrorResponse handleNotFoundException(RuntimeException ex, HttpServletRequest request) {
    return new ErrorResponse(
      LocalDateTime.now(),
      NOT_FOUND.value(),
      NOT_FOUND.toString(),
      ex.getMessage(),
      request.getRequestURI()
    );
  }

  @ExceptionHandler({
    LocationBadRequestException.class,
    BuildingBadRequestException.class,
    DeviceBadRequestException.class
  })
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleBadRequestException(RuntimeException ex, HttpServletRequest request) {
    return new ErrorResponse(
      LocalDateTime.now(),
      BAD_REQUEST.value(),
      BAD_REQUEST.toString(),
      ex.getMessage(),
      request.getRequestURI()
    );
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(FORBIDDEN)
  public ErrorResponse handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
    return new ErrorResponse(
      LocalDateTime.now(),
      FORBIDDEN.value(),
      FORBIDDEN.toString(),
      "Access denied",
      request.getRequestURI()
    );
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ErrorResponse handleException(Exception ex, HttpServletRequest request) {
    return new ErrorResponse(
      LocalDateTime.now(),
      INTERNAL_SERVER_ERROR.value(),
      INTERNAL_SERVER_ERROR.toString(),
      ex.getMessage(),
      request.getRequestURI()
    );
  }

}
