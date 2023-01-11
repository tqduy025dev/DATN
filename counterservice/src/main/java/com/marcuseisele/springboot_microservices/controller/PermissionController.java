package com.server.datn.server.controller;

import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.user.PermissionRequest;
import com.server.datn.server.common.dto.user.RoleRequest;
import com.server.datn.server.helper.PermissionHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/acceptance")
public class PermissionController {
    private final PermissionHelper helper;

    public PermissionController(PermissionHelper helper) {
        this.helper = helper;
    }

    @GetMapping( "/educed-permission")
    public ResponseEntity<?> findPermission(@RequestParam Map<String, String> map,
                                            @RequestParam(defaultValue = "true") boolean isPaging,
                                            @RequestParam(defaultValue = "0") Integer pageNo,
                                            @RequestParam(defaultValue = "20") Integer pageSize){
        Pageable pageable = isPaging ? PageRequest.of(pageNo, pageSize) : Pageable.unpaged();

        Response response = helper.findRolePermission("permission", map, pageable, isPaging);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-role")
    public ResponseEntity<?> findRole(@RequestParam Map<String, String> map,
                                      @RequestParam(defaultValue = "true") boolean isPaging,
                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                      @RequestParam(defaultValue = "20") Integer pageSize){
        Pageable pageable = isPaging ? PageRequest.of(pageNo, pageSize) : Pageable.unpaged();

        Response response = helper.findRolePermission( "role", map, pageable, isPaging);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-role/{id}")
    public ResponseEntity<?> findRoleById(@PathVariable String id){
        Response response = helper.findRoleById(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-permission/{id}")
    public ResponseEntity<?> findPermissioById(@PathVariable String id){
        Response response = helper.findPermissionById(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }


    @PostMapping("/fixed-role")
    public ResponseEntity<?> createRole(@RequestBody RoleRequest roleRequest){
        Response response = helper.createRole(roleRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/fixed-permission")
    public ResponseEntity<?> createPermission(@RequestBody PermissionRequest permissionRequest){
        Response response = helper.createPermission(permissionRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/fixed-role/{id}")
    public ResponseEntity<?> updateRole(@RequestBody RoleRequest roleRequest, @PathVariable String id){
        Response response = helper.updateRole(roleRequest, id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/fixed-permission/{id}")
    public ResponseEntity<?> updatePermission(@RequestBody PermissionRequest permissionRequest, @PathVariable String id){
        Response response = helper.updatePermission(permissionRequest, id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/fixed-role/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable String id){
        Response response = helper.deleteRole(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/fixed-permission/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable String id){
        Response response = helper.deletePermission(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }



}
