package com.server.datn.server.entity.job;

import com.server.datn.server.common.utils.KeyGenarator;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JobTitle {
    @Id
    private String jobTitleId;
    private String jobTitleName;
    private String description;

    public JobTitle() {
        this.jobTitleId = KeyGenarator.getKey();
    }

    public String getJobTitleId() {
        return jobTitleId;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
