package danielvishnievskyi.bachelorproject.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

/**
 * The RolesHierarchyConfig class is a Spring Configuration that provides a RoleHierarchy bean and a DefaultWebSecurityExpressionHandler bean.
 *
 * @author [Daniel Vishnievskyi].
 */
@Configuration
public class RolesHierarchyConfig {

  /**
   * Creates and returns a new DefaultWebSecurityExpressionHandler instance configured with a RoleHierarchy.
   *
   * @return A new DefaultWebSecurityExpressionHandler instance configured with a RoleHierarchy.
   */
  @Bean
  public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler() {
    DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
    handler.setRoleHierarchy(roleHierarchy());
    return handler;
  }

  /**
   * Creates and returns a new RoleHierarchy instance.
   *
   * @return A new RoleHierarchy instance configured with the hierarchy "ROLE_SUPER_ADMIN > ROLE_ADMIN_WRITE > ROLE_ADMIN_VIEW".
   */
  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    String hierarchy = "ROLE_SUPER_ADMIN > ROLE_ADMIN_WRITE > ROLE_ADMIN_VIEW";
    roleHierarchy.setHierarchy(hierarchy);
    return roleHierarchy;
  }
}