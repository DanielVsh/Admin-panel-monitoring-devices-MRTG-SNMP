package danielvishnievskyi.bachelorproject.controllers;

import danielvishnievskyi.bachelorproject.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class MainPageController {
  private final UserProfileService userProfileService;

  @GetMapping
  public String user(Principal principal) {
    if (principal == null) {
      return "user without login";
    }
    var user = userProfileService.getUserByUsername(principal.getName());
    return "user: " + user.getUsername();
  }

}
