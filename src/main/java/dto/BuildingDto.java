package dto;

import java.util.Collection;

import lombok.Data;

@Data
public class BuildingDto {
  private Long id;
  private String name;
  private Collection<Long> devicesIds;
  private Long locationId;
}
