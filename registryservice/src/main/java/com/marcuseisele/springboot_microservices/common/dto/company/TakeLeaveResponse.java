package com.server.datn.server.common.dto.company;

import com.server.datn.server.common.dto.employee.EmployeeResponse;
import com.server.datn.server.entity.manager.LeaveCategory;

public class TakeLeaveResponse {
    private String id;
    private String status;
    private String createdTime;
    private String takeLeaveFrom;
    private String takeLeaveTo;
    private String reason;
    private Integer daysOfLeave;
    private EmployeeResponse createBy;
    private EmployeeResponse reportTo;
    private LeaveCategory category;

    public EmployeeResponse getCreateBy() {
        return createBy;
    }

    public void setCreateBy(EmployeeResponse createBy) {
        this.createBy = createBy;
    }

    public EmployeeResponse getReportTo() {
        return reportTo;
    }

    public void setReportTo(EmployeeResponse reportTo) {
        this.reportTo = reportTo;
    }

    public LeaveCategory getCategory() {
        return category;
    }

    public void setCategory(LeaveCategory category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
