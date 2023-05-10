package danielvishnievskyi.bachelorproject.services.location;

import danielvishnievskyi.bachelorproject.dto.LocationDTO;
import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.exception.location.LocationBadRequestException;
import danielvishnievskyi.bachelorproject.exception.location.LocationNotFoundException;
import danielvishnievskyi.bachelorproject.repositories.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

  @Mock
  private LocationRepository locationRepository;

  @InjectMocks
  private LocationServiceImpl locationService;


  @Test
  void getFilteredAndPageableList() {
  }

  @Test
  void getEntityById_shouldReturnLocation() {
    Long id = 1L;
    Location location = new Location("Paris");

    when(locationRepository.findById(id)).thenReturn(Optional.of(location));

    Location result = locationService.getEntityById(id);

    assertNotNull(result);
    assertEquals("Paris", result.getName());

    verify(locationRepository).findById(id);
  }

  @Test
  void getEntityById_shouldThrowExceptionWhenNotFound() {
    Long id = 1L;

    when(locationRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(LocationNotFoundException.class, () -> locationService.getEntityById(id));

    verify(locationRepository).findById(id);
  }

  @Test
  void createEntity_shouldCreateNewEntityWithProvidedName() {
    String locationName = "New Location";
    LocationDTO locationDTO = new LocationDTO(locationName);

    when(locationRepository.findByName(locationName)).thenReturn(Optional.empty());

    Location expectedLocation = new Location(locationName);
    when(locationRepository.save(any(Location.class))).thenReturn(expectedLocation);

    Location createdLocation = locationService.createEntity(locationDTO);
    assertNotNull(createdLocation);
    assertEquals(expectedLocation.getName(), createdLocation.getName());

    verify(locationRepository).findByName(locationName);
    ArgumentCaptor<Location> locationArgumentCaptor = ArgumentCaptor.forClass(Location.class);
    verify(locationRepository).save(locationArgumentCaptor.capture());
    Location capturedLocation = locationArgumentCaptor.getValue();
    assertEquals(locationName, capturedLocation.getName());
  }

  @Test
  void createEntity_shouldThrowExceptionWhenLocationExists() {
    String existingLocationName = "Existing Location";
    LocationDTO locationDTO = new LocationDTO(existingLocationName);

    Location existingLocation = new Location(existingLocationName);
    when(locationRepository.findByName(existingLocationName)).thenReturn(Optional.of(existingLocation));

    assertThrows(LocationBadRequestException.class, () -> locationService.createEntity(locationDTO));

    verify(locationRepository).findByName(existingLocationName);

    verify(locationRepository, never()).save(any(Location.class));
  }

  @Test
  void updateEntity_shouldUpdateExistedEntityByProvidedDTO() {
    Location location = new Location("Location 1");

    when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
    when(locationRepository.save(any(Location.class))).thenReturn(new Location("result"));

    var result = locationService.updateEntity(1L, new LocationDTO("result"));

    assertNotNull(result);
    assertEquals("result", result.getName());

    verify(locationRepository).save(location);
    verify(locationRepository).findById(1L);

    ArgumentCaptor<Location> locationArgumentCaptor = ArgumentCaptor.forClass(Location.class);
    verify(locationRepository).save(locationArgumentCaptor.capture());
    Location capturedLocation = locationArgumentCaptor.getValue();
    assertEquals("result", capturedLocation.getName());
  }
}