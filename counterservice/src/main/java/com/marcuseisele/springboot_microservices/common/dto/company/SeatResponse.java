package com.server.datn.server.common.dto.company;

import com.server.datn.server.common.dto.employee.EmployeeResponse;

public class SeatResponse {
    private String seatId;
    private String status;
    private String styles;
    private EmployeeResponse employee;


    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
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

    public EmployeeResponse getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeResponse employee) {
        this.employee = employee;
    }
}
