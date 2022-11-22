package danielvishnievskyi.bachelorproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import danielvishnievskyi.bachelorproject.implementations.EnversRevImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@RevisionEntity(EnversRevImpl.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EnversRev extends DefaultRevisionEntity {
  private String username;
}
