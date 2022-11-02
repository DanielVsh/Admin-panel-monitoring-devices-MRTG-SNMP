package dto;

import java.util.Collection;

import lombok.Data;

@Data
public class LocationDto {
  private Long id;
  private String name;
  private Collection<Long> buildingsIds;
}
