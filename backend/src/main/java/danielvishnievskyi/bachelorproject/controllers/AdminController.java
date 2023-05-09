package danielvishnievskyi.bachelorproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import danielvishnievskyi.bachelorproject.services.admin.AdminProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@PreAuthorize("hasAnyRole('ADMIN_VIEW')")
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
  private final AdminProfileServiceImpl userProfileService;

  @GetMapping()
  public String admin(Principal principal) throws JsonProcessingException{
    return new ObjectMapper().writeValueAsString(userProfileService.getByUsername(principal.getName()));
  }
}
