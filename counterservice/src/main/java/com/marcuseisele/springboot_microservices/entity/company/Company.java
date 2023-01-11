package com.server.datn.server.entity.company;

import com.server.datn.server.common.constants.AppConstants;
import com.server.datn.server.common.utils.KeyGenarator;
import com.server.datn.server.entity.files.SystemFile;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    @Id
    private String companyId;
    @Column(unique = true)
    private String code;
    private String companyName;
    private String email;
    private String phone;
    private String status;
    private String address;
    private String startWorkTime;
    private String endWorkTime;
    @ElementCollection
    @JoinTable(name = "company_woking_day", joinColumns = @JoinColumn(name = "company_id"))
    private List<Integer> workDayOfWeek;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private SystemFile logo;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "company",cascade = CascadeType.ALL)
    private List<Office> offices;

    public Company() {
        this.companyId = KeyGenarator.getKey();
        this.status = AppConstants.STATUS_ACTIVE;
    }

    public SystemFile getLogo() {
        return logo;
    }

    public void setLogo(SystemFile logo) {
        this.logo = logo;
    }
    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public String getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public List<Integer> getWorkDayOfWeek() {
        return workDayOfWeek;
    }

    public void setWorkDayOfWeek(List<Integer> workDayOfWeek) {
        this.workDayOfWeek = workDayOfWeek;
    }
}
