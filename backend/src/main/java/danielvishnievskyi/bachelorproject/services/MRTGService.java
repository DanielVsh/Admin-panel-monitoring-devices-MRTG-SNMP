package danielvishnievskyi.bachelorproject.services;

import danielvishnievskyi.bachelorproject.entities.Device;
import danielvishnievskyi.bachelorproject.repositories.DeviceRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the MRTG service.
 * It is responsible for generating MRTG configuration files and HTML pages for a list of devices.
 * It uses SNMP to retrieve network traffic data from the devices.
 *
 * @author [Daniel Vishnievskyi].
 * <p>
 * MRTG configuration
 * @author [Ing. Martin Hasin].
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MRTGService {

  private final DeviceRepo deviceRepo;

  /**
   * Generates MRTG configuration files and HTML pages for all devices in the database.
   *
   * @throws IOException          if an I/O error occurs.
   * @throws InterruptedException if the current thread is interrupted.
   */
  @Transactional
  public void generateMRTG() throws IOException, InterruptedException {
    log.info("Start of generating MRTG");

    // SNMP version to use
    String snmpversion = "2";
    // StringBuilder to hold the command for generating the MRTG configuration file
    StringBuilder mrtgconf = new StringBuilder("/usr/bin/cfgmaker  --output=/tmp/mrtg-gen/mrtg.cfg.new  --global \"Interval: 5\" --global \"Forks: 4\"  --global \"options[_]: growright,bits\"  --ifdesc=descr --ifdesc=name --ifdesc=alias --show-op-down --no-down --subdirs=HOSTNAME__SNMPNAME ");

    // Create necessary directories
    Files.createDirectories(Path.of("/var/www/mrtg/"));
    Files.createDirectories(Paths.get("/tmp/mrtg-gen/"));
    Files.createDirectories(Paths.get("/var/www/html/mrtg"));

    // For each device, generate the MRTG configuration file and HTML page
    getIpList().stream().forEach(ip -> {
      try {
        String community = getCommunityByIp(ip);

        // Build the command for generating the MRTG configuration file
        String file = "/usr/bin/cfgmaker --output=/tmp/mrtg-gen/mrtg.cfg.new --global \"Interval: 5\" --global \"Forks: 4\"  --global \"options[_]: growright,bits\" --ifdesc=descr --show-op-down --ifdesc=name --ifdesc=alias --no-down --subdirs=HOSTNAME__SNMPNAME ";
        file += community + "@" + ip + ":::::" + snmpversion + ";";

        // Append the device to the StringBuilder for the overall MRTG configuration file
        mrtgconf
          .append(community)
          .append("@")
          .append(ip)
          .append(":::::")
          .append(snmpversion)
          .append(" ");

        // Create the script for generating the MRTG configuration file and execute it
        Path scriptPath = Paths.get("/var/www/html/mrtg.uvt.tuke.sk/admin/script/mrtgindex.sh");
        if (!Files.exists(scriptPath.getParent())) {
          Files.createDirectories(scriptPath.getParent());
        }
        Files.write(scriptPath, file.getBytes());
        executeShellCommand("bash", scriptPath.toString());

        // Generate the HTML page for the device
        executeShellCommand("indexmaker", "--output=/var/www/mrtg/" + ip + ".html", "/tmp/mrtg-gen/mrtg.cfg.new");
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    // Create the overall MRTG configuration file and execute it
    Path confPath = Paths.get("/var/www/html/mrtg.uvt.tuke.sk/admin/script/mrtgconf.sh");
    if (!Files.exists(confPath.getParent())) {
      Files.createDirectories(confPath.getParent());
    }
    // Write the MRTG configuration file to the specified path
    Files.write(confPath, mrtgconf.toString().getBytes());
    // Execute the MRTG configuration file using bash
    executeShellCommand("bash", confPath.toString());
    // Kill all running instances of MRTG
    executeShellCommand("killall", "mrtg");
    // Remove the old MRTG configuration file
    executeShellCommand("rm", "-rf", "/etc/mrtg.cfg.old");
    // Move the current MRTG configuration file to a backup location
    executeShellCommand("mv", "/etc/mrtg.cfg", "/etc/mrtg.cfg.old");
    // Copy the newly generated MRTG configuration file to the MRTG configuration file location
    executeShellCommand("cp", "-f", "/tmp/mrtg-gen/mrtg.cfg.new", "/etc/mrtg.cfg");
    // Remove the temporary directory used for generating the MRTG configuration file
    executeShellCommand("rm", "-rf", "/tmp/mrtg-gen/");

    // Remove the old MRTG HTML files
    executeShellCommand("rm", "-rf", "/var/www/html/mrtg");
    // Copy the new MRTG HTML files to the webserver directory
    executeShellCommand("cp", "-r", "/var/www/mrtg", "/var/www/html/");

    log.info("End of generating MRTG");
  }

  /**
   * Executes the given shell command in a new process and waits for it to finish.
   *
   * @param command The shell command to execute.
   * @throws IOException          If an I/O error occurs.
   * @throws InterruptedException If the current thread is interrupted while waiting for the process to finish.
   */
  private void executeShellCommand(String... command) throws IOException, InterruptedException {
    ProcessBuilder pb = new ProcessBuilder(command);
    pb.inheritIO();
    Process p = pb.start();
    p.waitFor();
  }

  /**
   * Retrieves a list of IP addresses from the device repository.
   *
   * @return A list of IP addresses.
   */
  public List<String> getIpList() {
    return deviceRepo.findAll()
      .stream()
      .map(Device::getIpAddress)
      .toList();
  }

  /**
   * Retrieves the SNMP community string for the specified IP address from the device repository.
   *
   * @param ip The IP address to retrieve the SNMP community string for.
   * @return The SNMP community string.
   */
  public String getCommunityByIp(String ip) {
    return deviceRepo.findAll()
      .stream()
      .filter(snmpData -> snmpData.getIpAddress().equals(ip))
      .map(Device::getSNMP)
      .collect(Collectors.joining())
      .toLowerCase();
  }
}