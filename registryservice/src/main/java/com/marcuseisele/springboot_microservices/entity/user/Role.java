package com.server.datn.server.entity.user;

import com.server.datn.server.common.constants.AppConstants;
import com.server.datn.server.common.utils.KeyGenarator;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {
    @Id
    private String roleId;
    @Column(unique = true)
    private String code;
    private String name;
    private String description;
    private String status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "permission_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Permission> permissions;

    public Role() {
        roleId = KeyGenarator.getKey();
        status = AppConstants.STATUS_ACTIVE;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getRoleId() {
        return roleId;
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
