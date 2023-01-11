package com.server.datn.server.entity.map;

import com.server.datn.server.common.constants.AppConstants;
import com.server.datn.server.common.utils.KeyGenarator;
import com.server.datn.server.entity.user.Employee;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Seat {
    @Id
    private String seatId;
    private String status;
    private String styles;
    @OneToOne
    private Employee employee;

    public Seat() {
        this.seatId = KeyGenarator.getKey();
        this.status = AppConstants.STATUS_ACTIVE;
    }

    public String getSeatId() {
        return seatId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStyles() {
        return styles;
    }

    public void setStyles(String styles) {
        this.styles = styles;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
