package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.services.MRTGService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@EnableAsync
@RestController()
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MRTGController {
  private final MRTGService MRTGService;

  @Scheduled(cron = "0 0 * * * ?")
  public void automaticallyGenerateMRTG() {
    try {
      MRTGService.generateMRTG();
      log.info("Successfully finished generating the MRTG");
    } catch (Exception e) {
      log.error("Error: {}", e.getMessage());
      e.printStackTrace();
    }
  }

  @GetMapping("/mrtg")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public void generateMRTG() {
    try {
      MRTGService.generateMRTG();
      log.info("Successfully finished generating the MRTG");
    } catch (Exception e) {
      log.error("Error: {}", e.getMessage());
      e.printStackTrace();
    }
  }
}
