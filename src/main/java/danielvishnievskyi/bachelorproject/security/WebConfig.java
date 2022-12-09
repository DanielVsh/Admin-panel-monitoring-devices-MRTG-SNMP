package danielvishnievskyi.bachelorproject.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
      .addMapping("/**")
      .allowedHeaders("*")
      .allowedMethods("*")
      .maxAge(60*60)
      .allowedOrigins("http://127.0.0.1:3000/", "http://147.232.205.203/");
  }
}