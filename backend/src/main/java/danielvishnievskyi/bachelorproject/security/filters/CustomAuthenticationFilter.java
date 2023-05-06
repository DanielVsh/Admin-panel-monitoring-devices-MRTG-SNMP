package danielvishnievskyi.bachelorproject.security.filters;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import danielvishnievskyi.bachelorproject.security.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The CustomAuthenticationFilter class is a custom implementation of the UsernamePasswordAuthenticationFilter provided by Spring Security.
 * It is used to authenticate a user using their username and password, and generate and return JWT tokens upon successful authentication.
 *
 * @author [Daniel Vishnievskyi].
 */
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;

  /**
   * Authenticates a user using their username and password and returns an Authentication instance.
   *
   * @param request  The HTTPServletRequest containing the user's credentials.
   * @param response The HTTPServletResponse to return the authentication result to.
   * @return An Authentication instance representing the authenticated user.
   * @throws AuthenticationException If authentication fails.
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
    return authenticationManager.authenticate(token);
  }

  /**
   * Generates and returns JWT tokens upon successful authentication.
   *
   * @param request    The HTTPServletRequest containing the user's credentials.
   * @param response   The HTTPServletResponse to return the authentication result to.
   * @param chain      The FilterChain to continue executing.
   * @param authResult The Authentication result of the successful authentication.
   * @throws IOException If there is an error writing the tokens to the response output stream.
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
    User user = (User) authResult.getPrincipal();
    String access_token = JWT.create()
      .withSubject(user.getUsername())
      .withExpiresAt(AuthUtil.getAccessTokenExpiresTime())
      .withIssuer(request.getRequestURL().toString())
      .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
      .sign(AuthUtil.getSecuredJWT());
    String refresh_token = JWT.create()
      .withSubject(user.getUsername())
      .withExpiresAt(AuthUtil.getRefreshTokenExpiresTime())
      .withIssuer(request.getRequestURL().toString())
      .sign(AuthUtil.getSecuredJWT());
    Map<String, String> tokens = new HashMap<>();
    tokens.put("access_token", access_token);
    tokens.put("refresh_token", refresh_token);
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
  }
}
