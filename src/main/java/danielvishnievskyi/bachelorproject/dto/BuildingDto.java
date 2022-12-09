package danielvishnievskyi.bachelorproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class BuildingDto {
  @NotBlank(message = "name should not be empty")
  private String name;
  @Min(value = 1, message = "id should be valid")
  private Long locationId;
}
