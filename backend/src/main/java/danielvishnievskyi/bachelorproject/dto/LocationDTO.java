package danielvishnievskyi.bachelorproject.dto;

import javax.validation.constraints.NotBlank;


/**
 * Data transfer object (DTO) for creating or updating a location. Contains the following parameter:
 * <p>
 * The {@code name} parameter is required and should not be blank.
 *
 * @param name a non-blank string representing the name of the location
 * @author [Daniel Vishnievskyi].
 */

public record LocationDTO(
  @NotBlank(message = "Name should not be empty") String name
) {
}
