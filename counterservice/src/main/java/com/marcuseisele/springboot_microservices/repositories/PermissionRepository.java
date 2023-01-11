package com.server.datn.server.repositories;

import com.server.datn.server.entity.user.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, String> {
    List<Permission> findPermissionsByCodeIn(List<String> codes);
    Permission findPermissionsByCode(String codes);
}
