package com.server.datn.server.services;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.user.PermissionRequest;
import com.server.datn.server.entity.user.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PermissionService {
    Permission createPermission(PermissionRequest permissionRequest);
    List<Permission> findPermissionByCode(List<String> codes);
    Permission findPermissionById(String id);
    BaseResult updatePermission(PermissionRequest permissionRequest, String id);
    Page<Permission> findAllPermission(Pageable pageable);
    String deletePermission(String id);
}
