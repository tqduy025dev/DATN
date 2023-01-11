package com.server.datn.server.services;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.job.JobRequest;
import com.server.datn.server.entity.job.JobTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobTitleService {
    JobTitle createJobTitle(JobRequest jobRequest);
    BaseResult updateJobTitle(JobRequest jobRequest, String id);
    String deleteJobTitle(String id);
    JobTitle findJobTitleById(String id);
    Page<JobTitle> findAllJobTitle(Pageable pageable);
}
