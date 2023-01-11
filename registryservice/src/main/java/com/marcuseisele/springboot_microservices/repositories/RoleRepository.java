package com.server.datn.server.repositories;

import com.server.datn.server.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, String> {
    List<Role> findRolesByRoleIdIn(List<String> ids);
}
