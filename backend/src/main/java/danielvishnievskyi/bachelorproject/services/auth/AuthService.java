package danielvishnievskyi.bachelorproject.services.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Service class for authentication related operations
 *
 * @author [Daniel Vishnievskyi].
 */
public interface AuthService {

  /**
   * Refreshes the authentication token using the refresh token provided in the request header.
   *
   * @param request  the HTTP request
   * @param response the HTTP response
   * @throws IOException if there is an error while writing to the response
   */
  void refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
