package danielvishnievskyi.bachelorproject.security;

import danielvishnievskyi.bachelorproject.security.filters.CustomAuthenticationFilter;
import danielvishnievskyi.bachelorproject.security.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * This class is responsible for configuring the security settings of the application. It is annotated with
 * {@code @EnableWebSecurity} to enable web security and {@code @EnableGlobalMethodSecurity(prePostEnabled = true)}
 * to enable method level security.
 * It is also annotated with {@code @RequiredArgsConstructor} which generates a constructor with required arguments.
 *
 * @author [Daniel Vishnievskyi].
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  /**
   * The user details service that provides user-related data.
   */
  private final UserDetailsService userDetailsService;
  /**
   * The password encoder used to encode and verify user passwords.
   */
  private final BCryptPasswordEncoder passwordEncoder;
  /**
   * The authentication configuration for the application.
   */
  private final AuthenticationConfiguration configuration;

  /**
   * This method configures the security filter chain that handles incoming requests to the application. It creates a
   * {@code CustomAuthenticationFilter} and sets its filterProcessesUrl to "/api/v1/auth/login". CSRF protection is
   * disabled, session creation policy is set to STATELESS, and all requests to endpoints "/api/v1/user/**" and
   * "/api/v1/auth/**" are permitted without authentication. A {@code CustomAuthorizationFilter} is also added to the
   * filter chain.
   *
   * @param http the HttpSecurity instance to configure
   * @return the configured security filter chain
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
    customAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
    http.csrf().disable();
//    http.cors().disable();
    http.cors(withDefaults());
//    http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests().antMatchers(
      "api/v1/user/**",
      "/api/v1/auth/**").permitAll();
//    http.authorizeRequests().anyRequest().authenticated();
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * This method creates a CORS filter that allows requests from specific origins and with specific headers and methods.
   *
   * @return the CORS filter instance
   */
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOriginPatterns(List.of(
      "http://147.232.205.203",
      "http://localhost:3000"
    ));
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.setMaxAge(60 * 60L);
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  /**
   * This method creates an instance of DaoAuthenticationProvider, which provides DAO-based authentication using the
   * provided user details service and password encoder.
   *
   * @return the authentication provider instance
   */
  @Bean
  public DaoAuthenticationProvider getDaoAuthenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }

  /**
   * This method retrieves the authentication manager from the authentication configuration.
   *
   * @return the authentication manager instance
   */
  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return configuration.getAuthenticationManager();
  }
}
