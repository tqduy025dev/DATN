package com.server.datn.server.services;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.user.RoleRequest;
import com.server.datn.server.entity.user.Permission;
import com.server.datn.server.entity.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    Role createRole(RoleRequest roleRequest, List<Permission> permissions);
    Page<Role> findAllRole(Pageable pageable);
    Role findRoleById(String id);
    BaseResult updateRole(RoleRequest roleRequest, String id);
    String deleteRole(String id);

}
