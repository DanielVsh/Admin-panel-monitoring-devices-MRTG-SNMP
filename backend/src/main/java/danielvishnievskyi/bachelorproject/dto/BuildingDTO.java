package danielvishnievskyi.bachelorproject.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


/**
 * Data transfer object (DTO) for creating or updating a building. Contains the following parameters:
 * The {@code name} and {@code locationId} parameters are required and should not be blank. The {@code locationId} should be a positive integer value.
 *
 * @param name       a non-blank string representing the name of the building
 * @param locationId a positive integer value representing the ID of the location where the building is located
 * @author [Daniel Vishnievskyi]
 */

public record BuildingDTO(
  @NotBlank(message = "Name should not be empty") String name,
  @Min(value = 1, message = "Id should be valid") Long locationId
) {
}
