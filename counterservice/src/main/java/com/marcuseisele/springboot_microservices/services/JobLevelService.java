package com.server.datn.server.services;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.job.JobRequest;
import com.server.datn.server.entity.job.JobLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobLevelService {
    JobLevel createJobLevel(JobRequest jobRequest);
    BaseResult updateJobLevel(JobRequest jobRequest, String id);
    JobLevel findJobLevelById(String id);
    String deleteLevel(String id);
    Page<JobLevel> findAllJobLevel(Pageable pageable);
}
