package com.demes.repository;

import com.demes.entity.Role;
import com.demes.entity.enums.RoleEnumeration;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleEnumeration name);
}
