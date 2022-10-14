package com.danielvishnievskyi.webjavareact.repositories;

import com.danielvishnievskyi.webjavareact.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
  Role findRoleByName(String name);
}
