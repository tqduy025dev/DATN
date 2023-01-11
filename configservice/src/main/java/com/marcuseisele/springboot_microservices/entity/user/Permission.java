package com.server.datn.server.entity.user;

import com.server.datn.server.common.constants.AppConstants;
import com.server.datn.server.common.utils.KeyGenarator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Permission {
    @Id
    private String permissionId;
    @Column(unique = true)
    private String code;
    private String name;
    private String description;
    private String status;
    @ManyToMany(mappedBy = "permissions",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    private List<Role> roles;

    public Permission() {
        permissionId = KeyGenarator.getKey();
        status = AppConstants.STATUS_ACTIVE;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
