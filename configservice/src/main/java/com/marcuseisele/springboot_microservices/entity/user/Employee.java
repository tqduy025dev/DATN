package com.server.datn.server.entity.user;

import com.server.datn.server.common.constants.AppConstants;
import com.server.datn.server.common.utils.KeyGenarator;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.account.Account;
import com.server.datn.server.entity.company.Office;
import com.server.datn.server.entity.files.SystemFile;
import com.server.datn.server.entity.job.JobLevel;
import com.server.datn.server.entity.job.JobTitle;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Set;

@Entity
public class Employee {
    @Id
    private String employeeId;
    @Column(unique = true, nullable = false)
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String phone;
    private String status;
    private String gender;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private SystemFile image;
    private Timestamp dayOfBirth;
    private Timestamp createdTime;
    @UpdateTimestamp
    private Timestamp lastUpdated;
    private String createBy;
    private String updateBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "office_id")
    private Office office;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_level_id")
    private JobLevel jobLevel;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_title_id")
    private JobTitle jobTitle;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "report_to")
    private Employee reportTo;

    @OneToMany(mappedBy = "reportTo")
    private Set<Employee> employees;

    private Integer daysOfLeave;

    public Employee() {
        Calendar calendar = Calendar.getInstance();
        this.employeeId = KeyGenarator.getKey();
        this.createdTime = TimeUtils.getTimestampNow();
        this.status = AppConstants.STATUS_ACTIVE;
        this.daysOfLeave = 12 - calendar.get(Calendar.MONTH);
    }

    public SystemFile getImage() {
        return image;
    }

    public void setImage(SystemFile image) {
        this.image = image;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getEmployeeId() {
        return employeeId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Timestamp getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Timestamp dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }


    public Employee getReportTo() {
        return reportTo;
    }

    public void setReportTo(Employee reportTo) {
        this.reportTo = reportTo;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getDaysOfLeave() {
        return daysOfLeave;
    }

    public void setDaysOfLeave(Integer daysOfLeave) {
        this.daysOfLeave = daysOfLeave;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            Employee another = (Employee) obj;
            if (this.employeeId.equals(another.employeeId)) {
                return true;
            }
        }
        return false;
    }
}
