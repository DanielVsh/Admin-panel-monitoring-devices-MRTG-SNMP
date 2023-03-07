package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.services.MRTGService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MRTGController {
  private final MRTGService MRTGService;

  @GetMapping("/mrtg")
  public String generateMRTG() {
    try {
      MRTGService.generateMRTG();
      return "mrtg-generated-successfully";
    } catch (Exception e) {
      e.printStackTrace();
      return "mrtg-generation-failed";
    }
  }
}
