package danielvishnievskyi.bachelorproject.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LocationDto {
  @NotBlank(message = "name should not be empty")
  private String name;
}
