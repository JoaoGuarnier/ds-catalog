package com.devsuperior.dscatalog.repository;

import com.devsuperior.dscatalog.model.Role;
import com.devsuperior.dscatalog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
