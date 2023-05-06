package danielvishnievskyi.bachelorproject.repositories.specifications;

import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * A base abstract class for building JPA {@link Specification}s based on a list of {@link SearchCriteria}.
 * Provides a default implementation for {@link Specification#toPredicate(Root, CriteriaQuery, CriteriaBuilder)} method.
 *
 * @param <T> the type of the entity being queried.
 * @author [Daniel Vishnievskyi].
 */
public abstract class BaseSpecification<T> implements Specification<T> {

  /**
   * A list of search criteria to build the specification.
   */
  private final List<SearchCriteria> list;

  /**
   * Constructs a new instance of {@code BaseSpecification} with an empty list of {@link SearchCriteria}.
   */
  public BaseSpecification() {
    this.list = new ArrayList<>();
  }

  /**
   * Adds a new search criteria to the list.
   *
   * @param criteria the search criteria to add
   */
  public void add(SearchCriteria criteria) {
    list.add(criteria);
  }

  /**
   * Builds and returns a {@link Predicate} for the JPA criteria query based on the list of search criteria.
   *
   * @param root    the root entity of the query
   * @param query   the criteria query object
   * @param builder the criteria builder object
   * @return the predicate built from the list of search criteria
   */
  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    var predicates =
      SpecificationUtil.getSpecificationSettings(list, root, query, builder);

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}