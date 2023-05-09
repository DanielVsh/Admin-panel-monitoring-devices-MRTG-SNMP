package danielvishnievskyi.bachelorproject.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface CrudService<T, G> {
  /**
   * Returns a page of entities of type {@literal T} that match the given filter
   * and pagination information.
   *
   * @param pageable the pagination information
   * @param filter   the filter to apply to the entities
   * @return a page of entities of type {@literal T} that match the given filter
   * and pagination information
   */
  Page<T> getFilteredAndPageableList(Pageable pageable, String filter);

  /**
   * Returns the entity of type {@literal T} by the given ID
   *
   * @param id the ID of the device to return
   * @return the entity of type {@literal T} by the given ID
   */
  T getEntityById(Long id);

  /**
   * Creates a new device with the given information, and returns the created device.
   *
   * @param entityDTO the information to use when creating the entity of type {@literal T}
   * @return the created entity of type {@literal T}
   */
  T createEntity(G entityDTO);

  /**
   * Updates the entity of type {@literal T} by the given ID using the information in the entityDTO, and returns the updated entity of type {@literal T}.
   *
   * @param id        the ID of the device to update
   * @param entityDTO the information to use when updating the device
   * @return the updated entity of type {@literal T}.
   */
  T updateEntity(Long id, G entityDTO);

  /**
   * Deletes the entity of type {@literal T} by the given ID.
   *
   * @param id the ID of the entity of type {@literal T} to delete.
   */
  void deleteEntityById(Long id);

  /**
   * Deletes the entity of type {@literal T} with the given IDs.
   *
   * @param ids the IDs of the entity of type {@literal T} to delete
   */
  void deleteEntitiesByIds(Set<Long> ids);
}
