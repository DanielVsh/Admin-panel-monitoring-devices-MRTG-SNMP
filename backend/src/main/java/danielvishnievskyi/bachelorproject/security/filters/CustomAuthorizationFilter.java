package danielvishnievskyi.bachelorproject.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import danielvishnievskyi.bachelorproject.security.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * CustomAuthorizationFilter is a custom filter used to authorize requests to protected endpoints by validating JWT tokens
 * sent in the Authorization header.
 * This filter extends OncePerRequestFilter to ensure that it is only executed once per request.
 * This filter will only authorize requests that are not for the login or token refresh endpoints.
 * If the JWT token is valid, the user's username and roles will be extracted from the token and added to the
 * SecurityContextHolder to allow access to protected endpoints.
 * If the JWT token is invalid, an error message will be sent in the response with an HTTP status code of 401 Unauthorized.
 *
 * @author [Daniel Vishnievskyi].
 */
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  /**
   * Overrides the doFilterInternal method to implement the custom authorization logic.
   *
   * @param request     the HTTP request
   * @param response    the HTTP response
   * @param filterChain the filter chain
   * @throws ServletException if there is a servlet exception
   * @throws IOException      if there is an IO exception
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if (request.getServletPath().equals("/api/v1/auth/login")
      || request.getServletPath().equals("/api/v1/auth/token/refresh")) {
      filterChain.doFilter(request, response);
    } else {
      String authorizationHeader = request.getHeader(AUTHORIZATION);
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        try {
          String token = authorizationHeader.substring("Bearer ".length());
          DecodedJWT decoded = JWT.require(AuthUtil.getSecuredJWT()).build().verify(token);
          String username = decoded.getSubject();
          String[] roles = decoded.getClaim("roles").asArray(String.class);
          List<GrantedAuthority> authorities = new ArrayList<>();
          stream(roles).forEach(s -> authorities.add(new SimpleGrantedAuthority(s)));
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            username, null, authorities
          );
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          filterChain.doFilter(request, response);
        } catch (Exception e) {
          log.error("Error logging to the system: {}", e.getMessage());
          response.setHeader("error", e.getMessage());
          response.setStatus(UNAUTHORIZED.value());
          Map<String, String> error = new HashMap<>();
          error.put("error_message", e.getMessage());
          response.setContentType(APPLICATION_JSON_VALUE);
          new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
      } else {
        filterChain.doFilter(request, response);
      }
    }
  }
}
