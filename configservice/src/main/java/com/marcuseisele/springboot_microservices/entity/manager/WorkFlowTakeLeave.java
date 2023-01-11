package com.server.datn.server.entity.manager;

import com.server.datn.server.common.constants.AppConstants;
import com.server.datn.server.common.utils.KeyGenarator;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.user.Employee;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@Entity
public class WorkFlowTakeLeave {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee createBy;
    @OneToOne
    @JoinColumn(name = "report_to")
    private Employee reportTo;
    private String status;
    private String reason;
    @UpdateTimestamp
    private Timestamp lastUpdated;
    private Timestamp createdTime;
    private Timestamp takeLeaveFrom;
    private Timestamp takeLeaveTo;
    private Integer daysOfLeave;
    @OneToOne
    @JoinColumn(name = "category_id")
    private LeaveCategory category;


    public WorkFlowTakeLeave() {
        this.id = KeyGenarator.getKey();
        this.createdTime = TimeUtils.getTimestampNow();
        this.status = AppConstants.STATUS_PENDING;
    }

    public String getId() {
        return id;
    }

    public Employee getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Employee createBy) {
        this.createBy = createBy;
    }

    public Employee getReportTo() {
        return reportTo;
    }

    public void setReportTo(Employee reportTo) {
        this.reportTo = reportTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getTakeLeaveFrom() {
        return takeLeaveFrom;
    }

    public void setTakeLeaveFrom(Timestamp takeLeaveFrom) {
        this.takeLeaveFrom = takeLeaveFrom;
    }

    public Timestamp getTakeLeaveTo() {
        return takeLeaveTo;
    }

    public void setTakeLeaveTo(Timestamp takeLeaveTo) {
        this.takeLeaveTo = takeLeaveTo;
    }

    public Integer getDaysOfLeave() {
        return daysOfLeave;
    }

    public void setDaysOfLeave(Integer daysOfLeave) {
        this.daysOfLeave = daysOfLeave;
    }

    public LeaveCategory getCategory() {
        return category;
    }

    public void setCategory(LeaveCategory category) {
        this.category = category;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
