package com.example.bds.repository;

import com.example.bds.entity.rbac.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {
    Roles findByRoleName(String roleName);
}
