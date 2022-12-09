package danielvishnievskyi.bachelorproject.repositories.specifications;

import danielvishnievskyi.bachelorproject.entities.Location;
import danielvishnievskyi.bachelorproject.repositories.criteria.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LocationSpecification implements Specification<Location> {
  private final List<SearchCriteria> list;

  public LocationSpecification() {
    this.list = new ArrayList<>();
  }

  public void add(SearchCriteria criteria) {
    list.add(criteria);
  }

  @Override
  public Predicate toPredicate(Root<Location> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    var predicates =
      SpecificationUtil.getSpecificationSettings(list, root, query, builder);

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
