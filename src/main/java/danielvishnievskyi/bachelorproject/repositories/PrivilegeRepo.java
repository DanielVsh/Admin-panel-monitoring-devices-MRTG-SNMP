package com.danielvishnievskyi.webjavareact.repositories;

import com.danielvishnievskyi.webjavareact.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepo extends JpaRepository<Privilege, Long> {
  Privilege findPrivilegeByName(String name);
}
