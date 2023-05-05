package danielvishnievskyi.bachelorproject.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public record BuildingDTO(
  @NotBlank(message = "Name should not be empty") String name,
  @Min(value = 1, message = "Id should be valid") Long locationId
) { }
