package danielvishnievskyi.bachelorproject.implementations;

import danielvishnievskyi.bachelorproject.entities.EnversRev;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class EnversRevImpl implements RevisionListener {
  @Override
  public void newRevision(Object revisionEntity) {
    EnversRev revEntity = (EnversRev) revisionEntity;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      revEntity.setUsername(authentication.getName());
    }
  }
}
