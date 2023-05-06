package danielvishnievskyi.bachelorproject.config;

import org.javers.spring.auditable.AuthorProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The SimpleAuthorProvider class provides the author of the Javers changeset using the Spring Security authentication object.
 *
 * @author [Daniel Vishnievskyi]
 */
public class SimpleAuthorProvider implements AuthorProvider {

  /**
   * Provides the author of the Javers changeset using the Spring Security authentication object.
   *
   * @return a string representing the name of the authenticated user.
   */
  @Override
  public String provide() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

  /**
   * Provides a bean of the SimpleAuthorProvider class to be used in the application context.
   *
   * @return a SimpleAuthorProvider object.
   */
  @Bean
  public AuthorProvider provideJaversAuthor() {
    return new SimpleAuthorProvider();
  }
}
