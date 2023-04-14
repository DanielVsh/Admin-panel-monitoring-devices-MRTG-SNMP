package danielvishnievskyi.bachelorproject.repositories.criteria;

import danielvishnievskyi.bachelorproject.enums.SearchOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
  private String key;
  private Object value;
  private SearchOperation operation;
}
