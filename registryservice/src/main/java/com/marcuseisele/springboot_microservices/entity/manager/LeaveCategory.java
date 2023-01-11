package com.server.datn.server.entity.manager;

import com.server.datn.server.common.utils.KeyGenarator;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LeaveCategory {
    @Id
    private String id;
    private String name;
    private Boolean payable;
    private String status;

    public LeaveCategory() {
        this.id = KeyGenarator.getKey();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPayable() {
        return payable;
    }

    public void setPayable(Boolean payable) {
        this.payable = payable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
