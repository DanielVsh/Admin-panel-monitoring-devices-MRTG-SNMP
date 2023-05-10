package danielvishnievskyi.bachelorproject.services.admin;

import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import danielvishnievskyi.bachelorproject.implementations.AdminProfileImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class that handles operations related to the AdminProfile entity.
 *
 * @author [Daniel Vishnievskyi].
 */
@Service
@RequiredArgsConstructor
public class AdminProfileServiceImpl {
  private final AdminProfileImpl userImpl;

  /**
   * Retrieves an AdminProfile object based on the provided username.
   *
   * @param username the username of the AdminProfile to retrieve
   * @return the AdminProfile object with the provided username, or null if it does not exist
   */
  public AdminProfile getByUsername(String username) {
    return userImpl.getByUsername(username);
  }

  /**
   * Saves an AdminProfile object.
   *
   * @param userProfile the AdminProfile object to save
   */
  public void save(AdminProfile userProfile) {
    userImpl.save(userProfile);
  }

  /**
   * Deletes an AdminProfile object.
   *
   * @param userProfile the AdminProfile object to delete
   */
  public void delete(AdminProfile userProfile) {
    userImpl.delete(userProfile);
  }
}
