package danielvishnievskyi.bachelorproject.repositories.criteria;

import danielvishnievskyi.bachelorproject.enums.SearchOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class representing a search criteria with a key, a value, and a search operation.
 *
 * @author [Daniel Vishnievskyi].
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

  /**
   * The key of the search criteria.
   */
  private String key;

  /**
   * The value of the search criteria.
   */
  private Object value;

  /**
   * The search operation to be applied on the key and value.
   */
  private SearchOperation operation;
}
