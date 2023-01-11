package com.server.datn.server.common.dto.company;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CompanyRequest {
    private String code;
    private String companyName;
    private String email;
    private String phone;
    private String address;
    private String startWorkTime;
    private String endWorkTime;
    private List<Integer> workDayOfWeek;
    private MultipartFile logo;

    public MultipartFile getLogo() {
        return logo;
    }

    public void setLogo(MultipartFile logo) {
        this.logo = logo;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
