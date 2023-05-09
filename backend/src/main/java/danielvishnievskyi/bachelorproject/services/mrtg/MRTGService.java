package danielvishnievskyi.bachelorproject.services.mrtg;

import java.io.IOException;

public interface MRTGService {

  /**
   * Generates MRTG configuration files and HTML pages for all devices in the database.
   *
   * @throws IOException          if an I/O error occurs.
   * @throws InterruptedException if the current thread is interrupted.
   */
  void generateMRTG() throws IOException, InterruptedException;
}
