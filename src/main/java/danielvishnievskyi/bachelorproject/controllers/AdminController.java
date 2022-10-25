package danielvishnievskyi.bachelorproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import danielvishnievskyi.bachelorproject.entities.AdminProfile;
import danielvishnievskyi.bachelorproject.services.AdminProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminProfileService userProfileService;

  @GetMapping()
  public String admin(Principal principal) throws JsonProcessingException{
    AdminProfile admin = userProfileService.getByUsername(principal.getName());
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(admin);
  }
}
