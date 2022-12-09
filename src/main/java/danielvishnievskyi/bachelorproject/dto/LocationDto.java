package danielvishnievskyi.bachelorproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LocationDto {
  @NotBlank(message = "name should not be empty")
  private String name;
}
