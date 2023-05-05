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

@Service
@Slf4j
@RequiredArgsConstructor
public class MRTGService {

  private final DeviceRepo deviceRepo;

  @Transactional
  public void generateMRTG() throws IOException, InterruptedException {
    log.info("Start of generating MRTG");

    String snmpversion = "2";
    StringBuilder mrtgconf = new StringBuilder("/usr/bin/cfgmaker  --output=/tmp/mrtg-gen/mrtg.cfg.new  --global \"Interval: 5\" --global \"Forks: 4\"  --global \"options[_]: growright,bits\"  --ifdesc=descr --ifdesc=name --ifdesc=alias --show-op-down --no-down --subdirs=HOSTNAME__SNMPNAME ");

    Files.createDirectories(Path.of("/var/www/mrtg/"));
    Files.createDirectories(Paths.get("/tmp/mrtg-gen/"));
    Files.createDirectories(Paths.get("/var/www/html/mrtg"));

    getIpList().stream().forEach(ip -> {
      try {
        String community = getCommunityByIp(ip);

        String file = "/usr/bin/cfgmaker --output=/tmp/mrtg-gen/mrtg.cfg.new --global \"Interval: 5\" --global \"Forks: 4\"  --global \"options[_]: growright,bits\" --ifdesc=descr --show-op-down --ifdesc=name --ifdesc=alias --no-down --subdirs=HOSTNAME__SNMPNAME ";
        file += community + "@" + ip + ":::::" + snmpversion + ";";
        mrtgconf
          .append(community)
          .append("@")
          .append(ip)
          .append(":::::")
          .append(snmpversion)
          .append(" ");

        Path scriptPath = Paths.get("/var/www/html/mrtg.uvt.tuke.sk/admin/script/mrtgindex.sh");
        if (!Files.exists(scriptPath.getParent())) {
          Files.createDirectories(scriptPath.getParent());
        }
        Files.write(scriptPath, file.getBytes());

        executeShellCommand("bash", scriptPath.toString());

        executeShellCommand("indexmaker", "--output=/var/www/mrtg/" + ip + ".html", "/tmp/mrtg-gen/mrtg.cfg.new");
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    Path confPath = Paths.get("/var/www/html/mrtg.uvt.tuke.sk/admin/script/mrtgconf.sh");
    if (!Files.exists(confPath.getParent())) {
      Files.createDirectories(confPath.getParent());
    }
    Files.write(confPath, mrtgconf.toString().getBytes());
    executeShellCommand("bash", confPath.toString());
    executeShellCommand("killall", "mrtg");
    executeShellCommand("rm", "-rf", "/etc/mrtg.cfg.old");
    executeShellCommand("mv", "/etc/mrtg.cfg", "/etc/mrtg.cfg.old");
    executeShellCommand("cp", "-f", "/tmp/mrtg-gen/mrtg.cfg.new", "/etc/mrtg.cfg");
    executeShellCommand("rm", "-rf", "/tmp/mrtg-gen/");

    executeShellCommand("rm", "-rf", "/var/www/html/mrtg");
    executeShellCommand("cp", "-r", "/var/www/mrtg", "/var/www/html/");

    log.info("End of generating MRTG");
  }

  private void executeShellCommand(String... command) throws IOException, InterruptedException {
    ProcessBuilder pb = new ProcessBuilder(command);
    pb.inheritIO();
    Process p = pb.start();
    p.waitFor();
  }

  public List<String> getIpList() {
    return deviceRepo.findAll()
      .stream()
      .map(Device::getIpAddress)
      .toList();
  }

  public String getCommunityByIp(String ip) {
    return deviceRepo.findAll()
      .stream()
      .filter(snmpData -> snmpData.getIpAddress().equals(ip))
      .map(Device::getSNMP)
      .collect(Collectors.joining())
      .toLowerCase();
  }
}