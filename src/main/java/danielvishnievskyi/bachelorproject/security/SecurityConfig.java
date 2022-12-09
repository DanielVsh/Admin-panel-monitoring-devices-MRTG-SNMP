package danielvishnievskyi.bachelorproject.security;

import danielvishnievskyi.bachelorproject.security.filters.CustomAuthenticationFilter;
import danielvishnievskyi.bachelorproject.security.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final AuthenticationConfiguration configuration;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
    customAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
    http.csrf().disable();
//    http.cors().disable();
    http.cors(withDefaults());
//    http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeHttpRequests().requestMatchers(
      "api/v1/user/**",
      "/api/v1/auth/**").permitAll();
//    http.authorizeRequests().anyRequest().authenticated();
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public DaoAuthenticationProvider getDaoAuthenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return configuration.getAuthenticationManager();
  }
}
