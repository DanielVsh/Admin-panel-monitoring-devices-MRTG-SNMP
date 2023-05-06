package danielvishnievskyi.bachelorproject.repositories.specifications;

import danielvishnievskyi.bachelorproject.entities.Location;
import org.springframework.data.jpa.domain.Specification;

/**
 * A {@link Specification} implementation for filtering and querying {@link Location} entities
 * based on a set of search criteria.
 * This class extends the {@link BaseSpecification} class and inherits its methods for
 * adding search criteria and constructing predicates to be used in the query.
 *
 * @author [Daniel Vishnievskyi].
 * @see BaseSpecification
 * @see Specification
 * @see Location
 */
public class LocationSpecification extends BaseSpecification<Location> {
}
