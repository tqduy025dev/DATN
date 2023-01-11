package com.server.datn.server.entity.manager;

import com.server.datn.server.common.dto.employee.EmployeeResponse;
import com.server.datn.server.common.dto.manager.WorkingTimeRequest;

import java.util.List;

public class WorkingTimeEmployee {
    private EmployeeResponse employee;
    private List<WorkingTimeRequest> workingTime;
    private Long totalWorkingDay;
    private Long totalWorkingDayThisMonth;
    private Long totalTakeLeave;
    private Long totalTakeOff;

    public Long getTotalWorkingDay() {
        return totalWorkingDay;
    }

    public void setTotalWorkingDay(Long totalWorkingDay) {
        this.totalWorkingDay = totalWorkingDay;
    }

    public Long getTotalWorkingDayThisMonth() {
        return totalWorkingDayThisMonth;
    }

    public void setTotalWorkingDayThisMonth(Long totalWorkingDayThisMonth) {
        this.totalWorkingDayThisMonth = totalWorkingDayThisMonth;
    }

    public EmployeeResponse getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeResponse employee) {
        this.employee = employee;
    }

    public List<WorkingTimeRequest> getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(List<WorkingTimeRequest> workingTime) {
        this.workingTime = workingTime;
    }

    public Long getTotalTakeLeave() {
        return totalTakeLeave;
    }

    public void setTotalTakeLeave(Long totalTakeLeave) {
        this.totalTakeLeave = totalTakeLeave;
    }

    public Long getTotalTakeOff() {
        return totalTakeOff;
    }

    public void setTotalTakeOff(Long totalTakeOff) {
        this.totalTakeOff = totalTakeOff;
    }
}
