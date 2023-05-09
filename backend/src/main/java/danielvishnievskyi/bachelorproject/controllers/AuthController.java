package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The AuthController class represents the REST API endpoints for handling authentication.
 *
 * @author [Daniel Vishnievskyi]
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  /**
   * The authService instance used for auth related operations.
   */
  private final AuthService authService;

  /**
   * GET endpoint to refresh the authentication token.
   *
   * @param request  the HTTP servlet request.
   * @param response the HTTP servlet response.
   * @throws IOException if an I/O error occurs while handling the request.
   */
  @GetMapping("/token/refresh")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    authService.refreshAuthenticationToken(request, response);
  }
}
