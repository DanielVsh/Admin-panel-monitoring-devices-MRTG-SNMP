package danielvishnievskyi.bachelorproject.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class BuildingDto {
  @NotBlank(message = "name should not be empty")
  private String name;
  @Min(value = 1, message = "id should be valid")
  private Long locationId;
}
