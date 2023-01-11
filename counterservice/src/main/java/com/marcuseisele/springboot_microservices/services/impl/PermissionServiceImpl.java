package com.server.datn.server.services.impl;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.user.PermissionRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.entity.user.Permission;
import com.server.datn.server.repositories.PermissionRepository;
import com.server.datn.server.services.PermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.server.datn.server.common.constants.AppConstants.*;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;


    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission createPermission(PermissionRequest permissionRequest) {
        Permission permission = (Permission) AppUtils.converToEntities(permissionRequest, Permission.class);
        return permissionRepository.save(permission);
    }

    @Override
    public List<Permission> findPermissionByCode(List<String> codes) {
        return permissionRepository.findPermissionsByCodeIn(codes);
    }

    @Override
    public Permission findPermissionById(String id) {
        return permissionRepository.findById(id).orElse(null);
    }

    @Override
    public BaseResult updatePermission(PermissionRequest permissionRequest, String id) {
        BaseResult result = new BaseResult();
        String status = STATUS_U2;
        boolean exists = permissionRepository.existsById(id);
        if (exists) {
            status = STATUS_U0;
            Permission permissionOld = permissionRepository.getReferenceById(id);
            final String perCode = permissionOld.getCode();
            AppUtils.copyNonNullProperties(permissionRequest, permissionOld);

            permissionOld.setCode(perCode);
            Permission permission = permissionRepository.save(permissionOld);
            result.setResult(permission);
        }
        result.setStatus(status);
        return result;
    }

    @Override
    public Page<Permission> findAllPermission(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    @Override
    public String deletePermission(String id) {
        String status = STATUS_D2;
        boolean exists = permissionRepository.existsById(id);
        if (exists){
            Permission permission = permissionRepository.getReferenceById(id);
            if(STATUS_DELETE.equals(permission.getStatus())){
                status = STATUS_D1;
            }else {
                permission.setStatus(STATUS_DELETE);
                permissionRepository.save(permission);
                status = STATUS_D0;
            }
        }
        return status;
    }
}
