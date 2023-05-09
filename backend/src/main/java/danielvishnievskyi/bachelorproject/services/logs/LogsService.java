package danielvishnievskyi.bachelorproject.services.logs;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * The LogsService class provides functionality for retrieving and filtering logs from the Javers auditing library.
 *
 * @author [Daniel Vishnievskyi].
 */
public interface LogsService {
  /**
   * Returns a page of changes for the specified time range and filter. Changes are retrieved from the Javers auditing library.
   *
   * @param pageable - Paging and sorting information
   * @param filter   - Filter to apply to the changes. Supported filters are by Location, Building and Device.
   * @param timeFrom - The start of the time range for the changes.
   * @param timeTo   - The end of the time range for the changes.
   * @return A JSON string representation of a map containing the page of changes and total number of pages.
   */
  String getLogs(Pageable pageable, String filter, LocalDate timeFrom, LocalDate timeTo);
}
