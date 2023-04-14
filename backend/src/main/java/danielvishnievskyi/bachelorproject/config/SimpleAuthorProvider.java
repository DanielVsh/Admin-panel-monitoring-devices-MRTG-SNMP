package danielvishnievskyi.bachelorproject.config;

import org.javers.spring.auditable.AuthorProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SimpleAuthorProvider implements AuthorProvider {
  @Override
  public String provide() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

  @Bean
  public AuthorProvider provideJaversAuthor() {
    return new SimpleAuthorProvider();
  }
}
