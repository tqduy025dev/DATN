package com.server.datn.server.common.dto.employee;

import com.server.datn.server.common.dto.base.FileResponse;
import com.server.datn.server.common.dto.company.OfficeResponse;
import com.server.datn.server.common.dto.manager.WorkingTimeRequest;
import com.server.datn.server.common.dto.user.RoleResponse;
import com.server.datn.server.entity.job.JobLevel;
import com.server.datn.server.entity.job.JobTitle;

import java.util.List;

public class EmployeeResponse {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private String gender;
    private String dayOfbirth;
    private String createTime;
    private String lastUpdated;
    private String status;
    private OfficeResponse office;
    private JobLevel jobLevel;
    private JobTitle jobTitle;
    private RoleResponse role;
    private FileResponse image;
    private EmployeeResponse reportTo;
    private List<EmployeeResponse> childrens;
    private Integer daysOfLeave;
    private WorkingTimeRequest workingTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDayOfbirth() {
        return dayOfbirth;
    }

    public void setDayOfbirth(String dayOfbirth) {
        this.dayOfbirth = dayOfbirth;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public FileResponse getImage() {
        return image;
    }

    public void setImage(FileResponse image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RoleResponse getRole() {
        return role;
    }

    public void setRole(RoleResponse role) {
        this.role = role;
    }

    public OfficeResponse getOffice() {
        return office;
    }

    public void setOffice(OfficeResponse office) {
        this.office = office;
    }

    public JobLevel getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public EmployeeResponse getReportTo() {
        return reportTo;
    }

    public void setReportTo(EmployeeResponse reportTo) {
        this.reportTo = reportTo;
    }

    public List<EmployeeResponse> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<EmployeeResponse> childrens) {
        this.childrens = childrens;
    }

    public Integer getDaysOfLeave() {
        return daysOfLeave;
    }

    public void setDaysOfLeave(Integer daysOfLeave) {
        this.daysOfLeave = daysOfLeave;
    }

    public WorkingTimeRequest getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(WorkingTimeRequest workingTime) {
        this.workingTime = workingTime;
    }
}
