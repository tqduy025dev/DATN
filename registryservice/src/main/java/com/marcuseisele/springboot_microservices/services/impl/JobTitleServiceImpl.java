package com.server.datn.server.services.impl;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.job.JobRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.entity.job.JobTitle;
import com.server.datn.server.repositories.JobTitleRepository;
import com.server.datn.server.services.JobTitleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.server.datn.server.common.constants.AppConstants.*;

@Service
public class JobTitleServiceImpl implements JobTitleService {
    private final JobTitleRepository jobTitleRepository;

    public JobTitleServiceImpl(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }

    @Override
    public JobTitle createJobTitle(JobRequest jobRequest) {
        JobTitle jobTitle = (JobTitle) AppUtils.converToEntities(jobRequest, JobTitle.class);
        return jobTitleRepository.save(jobTitle);
    }

    @Override
    public BaseResult updateJobTitle(JobRequest jobRequest, String id) {
        BaseResult result = new BaseResult();
        String status = STATUS_U2;
        boolean exists = jobTitleRepository.existsById(id);
        if (exists) {
            status = STATUS_U0;
            JobTitle jobTitleOld = jobTitleRepository.getReferenceById(id);
            AppUtils.copyNonNullProperties(jobRequest, jobTitleOld);
            JobTitle jobTitle = jobTitleRepository.save(jobTitleOld);
            result.setResult(jobTitle);
        }
        result.setStatus(status);
        return result;
    }

    @Override
    public String deleteJobTitle(String id) {
        String status = STATUS_D2;
        boolean exists = jobTitleRepository.existsById(id);
        if (exists){
            JobTitle jobTitle = jobTitleRepository.getReferenceById(id);
            jobTitleRepository.delete(jobTitle);
            status = STATUS_D0;
        }
        return status;
    }

    @Override
    public JobTitle findJobTitleById(String id) {
        return jobTitleRepository.findById(id).orElse(null);
    }

    @Override
    public Page<JobTitle> findAllJobTitle(Pageable pageable) {
        return jobTitleRepository.findAll(pageable);
    }
}
