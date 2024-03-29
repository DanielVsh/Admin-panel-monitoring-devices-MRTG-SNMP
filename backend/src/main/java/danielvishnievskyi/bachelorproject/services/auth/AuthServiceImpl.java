package danielvishnievskyi.bachelorproject.services.auth;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import danielvishnievskyi.bachelorproject.entities.Role;
import danielvishnievskyi.bachelorproject.security.utils.AuthUtil;
import danielvishnievskyi.bachelorproject.services.admin.AdminProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
  private final AdminProfileServiceImpl userProfileService;

  @Override
  public void refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
