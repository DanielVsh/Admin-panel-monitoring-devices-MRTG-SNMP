package danielvishnievskyi.bachelorproject.enums;

/**
 * Enum representing various search operations that can be used in a search query.
 *
 * @author [Daniel Vishnievskyi].
 */
public enum SearchOperation {
  /*
  Represents the ">" operator in a search query, to search for values greater than a given value.
  */
  GREATER_THAN,
  /**
   * Represents the "<" operator in a search query, to search for values less than a given value.
   */
  LESS_THAN,
  /**
   * Represents the ">=" operator in a search query, to search for values greater than or equal to a given value.
   */
  GREATER_THAN_EQUAL,
  /**
   * Represents the "<=" operator in a search query, to search for values less than or equal to a given value.
   */
  LESS_THAN_EQUAL,
  /**
   * Represents the "!=" operator in a search query, to search for values not equal to a given value.
   */
  NOT_EQUAL,
  /**
   * Represents the "=" operator in a search query, to search for values equal to a given value.
   */
  EQUAL,
  /**
   * Represents the "LIKE" operator in a search query, to search for values that match a given pattern.
   */
  MATCH,
  /**
   * Represents the "LIKE %value" operator in a search query, to search for values that start with a given pattern.
   */
  MATCH_START,
  /**
   * Represents the "LIKE value%" operator in a search query, to search for values that end with a given pattern.
   */
  MATCH_END,
  /**
   * Represents the "IN" operator in a search query, to search for values that are contained in a given set.
   */
  IN,
  /**
   * Represents the "NOT IN" operator in a search query, to search for values that are not contained in a given set.
   */
  NOT_IN
}