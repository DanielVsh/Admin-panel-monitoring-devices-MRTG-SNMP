package danielvishnievskyi.bachelorproject.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * The PasswordEncoderConfig class is a Spring Component that provides a BCryptPasswordEncoder bean.
 *
 * @author [Daniel Vishnievskyi].
 */
@Component
public class PasswordEncoderConfig {

  /**
   * Creates and returns a new BCryptPasswordEncoder instance with the given strength.
   *
   * @return A new BCryptPasswordEncoder instance with a strength of 12.
   */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
}

