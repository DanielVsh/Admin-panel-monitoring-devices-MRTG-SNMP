package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Building;
import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.entities.Location;
import lombok.RequiredArgsConstructor;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogsService {
  private final Javers javers;

  public String getLogs(Pageable pageable, String filter, LocalDate timeFrom, LocalDate timeTo) {
    QueryBuilder jql = QueryBuilder.byClass(
        Location.class,
        Building.class,
        Device.class
      )
      .from(timeFrom)
      .to(timeTo);

    var changes = javers.findChanges(
      jql
        .limit(300)
        .build()
    );

    int startIndex = pageable.getPageNumber() * pageable.getPageSize();
    int endIndex = Math.min(startIndex + pageable.getPageSize(), changes.size());

    Page<Change> page = new PageImpl<>(changes.subList(startIndex, endIndex), pageable, changes.size());
    Map<String, Object> response = new HashMap<>();
    response.put("page", page);
    response.put("totalPages", page.getTotalPages());
    return javers.getJsonConverter().toJson(response);
  }
}
