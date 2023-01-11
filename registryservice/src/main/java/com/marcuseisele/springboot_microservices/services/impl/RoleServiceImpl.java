package com.server.datn.server.services.impl;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.user.RoleRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.entity.user.Permission;
import com.server.datn.server.entity.user.Role;
import com.server.datn.server.repositories.RoleRepository;
import com.server.datn.server.services.PermissionService;
import com.server.datn.server.services.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static com.server.datn.server.common.constants.AppConstants.*;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionService permissionService;



    public RoleServiceImpl(RoleRepository roleRepository, PermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }


    @Override
    public Role createRole(RoleRequest roleRequest, List<Permission> permissions) {
        Role role = (Role) AppUtils.converToEntities(roleRequest, Role.class);
        role.setPermissions(new HashSet<>(permissions));
        return roleRepository.save(role);
    }

    @Override
    public Page<Role> findAllRole(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }


    @Override
    public Role findRoleById(String id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public BaseResult updateRole(RoleRequest roleRequest, String id) {
        BaseResult result = new BaseResult();
        String status = STATUS_U2;
        boolean exists = roleRepository.existsById(id);
        if (exists) {
            status = STATUS_U0;
            Role roleOld = roleRepository.getReferenceById(id);
            final String roleCode = roleOld.getCode();
            AppUtils.copyNonNullProperties(roleRequest, roleOld);

            if(Objects.nonNull(roleRequest.getPermissions())){
                List<Permission> permissions = permissionService.findPermissionByCode(roleRequest.getPermissions());
                roleOld.setPermissions(new HashSet<>(permissions));
            }
            roleOld.setCode(roleCode);
            Role role = roleRepository.save(roleOld);
            result.setResult(role);
        }
        result.setStatus(status);
        return result;
    }

    @Override
    public String deleteRole(String id) {
        String status = STATUS_D2;
        boolean exists = roleRepository.existsById(id);
        if (exists){
            Role role = roleRepository.getReferenceById(id);
            if(STATUS_DELETE.equals(role.getStatus())){
                status = STATUS_D1;
            }else {
                role.setStatus(STATUS_DELETE);
                roleRepository.save(role);
                status = STATUS_D0;
            }
        }
        return status;
    }
}
