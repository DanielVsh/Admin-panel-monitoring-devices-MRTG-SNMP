package danielvishnievskyi.bachelorproject.dto;

import javax.validation.constraints.NotBlank;

public record LocationDTO (
  @NotBlank(message = "Name should not be empty") String name
) { }
