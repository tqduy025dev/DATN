package com.server.datn.server.common.dto.company;

public class TakeLeaveRequest {
    private String employeeId;
    private String takeLeaveFrom;
    private String takeLeaveTo;
    private Integer daysOfLeave;
    private String categoryId;
    private String reason;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTakeLeaveFrom() {
        return takeLeaveFrom;
    }

    public void setTakeLeaveFrom(String takeLeaveFrom) {
        this.takeLeaveFrom = takeLeaveFrom;
    }

    public String getTakeLeaveTo() {
        return takeLeaveTo;
    }

    public void setTakeLeaveTo(String takeLeaveTo) {
        this.takeLeaveTo = takeLeaveTo;
    }

    public Integer getDaysOfLeave() {
        return daysOfLeave;
    }

    public void setDaysOfLeave(Integer daysOfLeave) {
        this.daysOfLeave = daysOfLeave;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
