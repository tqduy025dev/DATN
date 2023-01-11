package com.server.datn.server.entity.manager;

import com.server.datn.server.common.utils.KeyGenarator;
import com.server.datn.server.common.utils.TimeUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class WorkingTime {
    @Id
    private String id;
    private String employeeId;
    private String type;
    private String location;
    private Timestamp time;
    public WorkingTime(){
        this.id = KeyGenarator.getKey();
        this.time = TimeUtils.getTimestampNow();
    }

    public String getId() {
        return id;
    }


    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getTime() {
        return time;
    }

}
