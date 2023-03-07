package danielvishnievskyi.bachelorproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BachelorProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(BachelorProjectApplication.class, args);
  }
}
