package danielvishnievskyi.bachelorproject.repositories;

import danielvishnievskyi.bachelorproject.entities.BuildingsLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingLocationRepo extends JpaRepository<BuildingsLocation, Long> {
  Optional<BuildingsLocation> getByName(String name);
}
