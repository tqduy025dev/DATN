package com.server.datn.server.helper;


import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.base.ResponseData;
import com.server.datn.server.common.dto.base.ResponseResult;
import com.server.datn.server.common.dto.user.PermissionRequest;
import com.server.datn.server.common.dto.user.PermissionResponse;
import com.server.datn.server.common.dto.user.RoleRequest;
import com.server.datn.server.common.dto.user.RoleResponse;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.PagingUtils;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.entity.user.Permission;
import com.server.datn.server.entity.user.Role;
import com.server.datn.server.services.PermissionService;
import com.server.datn.server.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import static com.server.datn.server.common.constants.AppConstants.FAIL_CODE;
import static com.server.datn.server.common.constants.AppConstants.SUCC_CODE;
import static com.server.datn.server.common.constants.MessageConstants.*;

@Service
public class PermissionHelper {
    private final Logger logger = LoggerFactory.getLogger(PermissionHelper.class);

    private final RoleService roleService;
    private final PermissionService permissionService;

    private final Response response = new Response();

    public PermissionHelper(RoleService roleService, PermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }


    public Response createRole(RoleRequest roleRequest){
        ResponseData responseData = new ResponseData();
        ResponseResult responseResult = null;
        try {
            List<Permission> permissions = permissionService.findPermissionByCode(roleRequest.getPermissions());
            if(!CollectionUtils.isEmpty(permissions)){
                Role result = roleService.createRole(roleRequest, permissions);
                Object roleResponse = AppUtils.converToDTO(result, RoleResponse.class);
                responseData.setData(roleResponse);
                responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC, SUCC_CODE);
            }

        }catch (Exception e){
            logger.error("****************RoleHelper ERROR createRole()***************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL, FAIL_CODE);
            responseData = null;
        }

        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    public Response createPermission(PermissionRequest permissionRequest){
        ResponseData responseData = new ResponseData();
        ResponseResult responseResult;
        try {
            Permission result = permissionService.createPermission(permissionRequest);
            Object permissionResponse = AppUtils.converToDTO(result, PermissionResponse.class);
            responseData.setData(permissionResponse);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC, SUCC_CODE);
        }catch (Exception e){
            logger.error("****************RoleHelper ERROR createPermission()***************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL, FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }


    public Response updateRole(RoleRequest roleRequest, String id){
        ResponseData responseData = new ResponseData();
        ResponseResult responseResult;
        try {
            BaseResult result = roleService.updateRole(roleRequest, id);
            responseResult = ResponseResultUtils.getResponseResult(result.getStatus());
            Object seatMapResponses = AppUtils.converToDTO(result.getResult(), RoleResponse.class);
            responseData.setData(seatMapResponses);
        }catch (Exception e){
            logger.error("****************RoleHelper ERROR createRole()***************", e);
            responseResult = ResponseResultUtils.getResponseResult(UPDATE_FAIL, FAIL_CODE);
            responseData = null;
        }

        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    public Response updatePermission(PermissionRequest permissionRequest, String id){
        ResponseData responseData = new ResponseData();
        ResponseResult responseResult;
        try {
            BaseResult result = permissionService.updatePermission(permissionRequest, id);
            Object permissionResponse = AppUtils.converToDTO(result.getResult(), PermissionResponse.class);
            responseResult = ResponseResultUtils.getResponseResult(result.getStatus());
            responseData.setData(permissionResponse);
        }catch (Exception e){
            logger.error("****************RoleHelper ERROR createPermission()***************", e);
            responseResult = ResponseResultUtils.getResponseResult(UPDATE_FAIL, FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }


    public Response findRolePermission(String func,Map<String, String> map, Pageable pageable, boolean isPaging){
        ResponseResult responseResult;
        try {
            switch (func){
                case "permission" :
                    ResponseData permissionResponses = this.findAllPermission(pageable, isPaging);
                    response.setResponse(permissionResponses);
                    break;
                case "role" :
                    ResponseData roleResponses = this.findAllRole(pageable, isPaging);
                    response.setResponse(roleResponses);
                    break;
            }
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
        }catch (Exception e){
            logger.error("****************RoleHelper ERROR createPermission()***************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
        }

        response.setResult(responseResult);
        return response;
    }

    public Response findRoleById(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Role role = roleService.findRoleById(id);
            Object roleResponses = AppUtils.converToDTO(role, RoleResponse.class);
            responseData.setData(roleResponses);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
        }catch (Exception e){
            logger.error("****************RoleHelper ERROR findRoleById()***************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    public Response findPermissionById(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Permission permission = permissionService.findPermissionById(id);
            Object permissionResponse = AppUtils.converToDTO(permission, PermissionResponse.class);
            responseData.setData(permissionResponse);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
        }catch (Exception e){
            logger.error("****************RoleHelper ERROR findPermissionById()***************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }


    private ResponseData findAllRole(Pageable pageable, boolean isPaging) {
        ResponseData responseData = new ResponseData();
        Page<Role> roles = roleService.findAllRole(pageable);
        Object roleResponses = AppUtils.converToDTO(roles.getContent(), RoleResponse[].class);
        if(isPaging){
            PagingUtils.setDataResponse(responseData, roles);
        }
        responseData.setData(roleResponses);
        return responseData;
    }

    private ResponseData findAllPermission(Pageable pageable, boolean isPaging){
        ResponseData responseData = new ResponseData();
        Page<Permission> permissions = permissionService.findAllPermission(pageable);
        Object permissionResponses = AppUtils.converToDTO(permissions.getContent(), PermissionResponse[].class);
        if(isPaging){
            PagingUtils.setDataResponse(responseData, permissions);
        }
        responseData.setData(permissionResponses);
        return responseData;
    }

    public Response deleteRole(String id){
        ResponseData responseData = new ResponseData();
        ResponseResult responseResult;
        try {
            String result = roleService.deleteRole(id);
            responseResult = ResponseResultUtils.getResponseResult(result);
        }catch (Exception e){
            logger.error("****************RoleHelper ERROR createRole()***************", e);
            responseResult = ResponseResultUtils.getResponseResult(DELETE_FAIL, FAIL_CODE);
            responseData = null;
        }

        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    public Response deletePermission(String id){
        ResponseData responseData = new ResponseData();
        ResponseResult responseResult;
        try {
            String result = permissionService.deletePermission(id);
            responseResult = ResponseResultUtils.getResponseResult(result);
        }catch (Exception e){
            logger.error("****************RoleHelper ERROR createPermission()***************", e);
            responseResult = ResponseResultUtils.getResponseResult(DELETE_FAIL, FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }


}
