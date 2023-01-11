package com.server.datn.server.entity.job;

import com.server.datn.server.common.utils.KeyGenarator;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JobLevel {
    @Id
    private String jobLevelId;
    private String jobLevelName;
    private String description;
    private Integer level;

    public JobLevel() {
        this.jobLevelId = KeyGenarator.getKey();
        this.level = 1;
    }

    public String getJobLevelId() {
        return jobLevelId;
    }

    public void setJobLevelId(String jobLevelId) {
        this.jobLevelId = jobLevelId;
    }

    public String getJobLevelName() {
        return jobLevelName;
    }

    public void setJobLevelName(String jobLevelName) {
        this.jobLevelName = jobLevelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
