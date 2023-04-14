package danielvishnievskyi.bachelorproject.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
public class RolesHierarchyConfig {
  @Bean
  public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler() {
    DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
    handler.setRoleHierarchy(roleHierarchy());
    return handler;
  }

  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    String hierarchy = "ROLE_SUPER_ADMIN > ROLE_ADMIN_WRITE > ROLE_ADMIN_VIEW";
    roleHierarchy.setHierarchy(hierarchy);
    return roleHierarchy;
  }
}