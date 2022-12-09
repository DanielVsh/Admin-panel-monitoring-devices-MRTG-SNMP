package danielvishnievskyi.bachelorproject.controllers;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import danielvishnievskyi.bachelorproject.entities.Role;
import danielvishnievskyi.bachelorproject.security.utils.AuthUtil;
import danielvishnievskyi.bachelorproject.services.AdminProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AdminProfileService userProfileService;

  @GetMapping()
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Boolean> isLogged() {
    return ResponseEntity.ok().body(true);
  }

  @GetMapping("/token/refresh")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String authorizationHeader = request.getHeader(AUTHORIZATION);
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      try {
        String refresh_token = authorizationHeader.substring("Bearer ".length());
        String username = JWT.require(AuthUtil.getSecuredJWT()).build().verify(refresh_token).getSubject();
        AdminProfile userProfile = userProfileService.getByUsername(username);
        String access_token = JWT.create()
          .withSubject(userProfile.getUsername())
          .withExpiresAt(AuthUtil.getAccessTokenExpiresTime())
          .withIssuer(request.getRequestURL().toString())
          .withClaim("roles", userProfile.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
          .sign(AuthUtil.getSecuredJWT());
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
      } catch (Exception e) {
        log.error("Error logging to the system: {}", e.getMessage());
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_message", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
      }
    } else {
      throw new RuntimeException("Refresh token is missing");
    }
  }
}
