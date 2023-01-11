package com.server.datn.server.services.impl;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.job.JobRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.entity.job.JobLevel;
import com.server.datn.server.repositories.JobLevelRepository;
import com.server.datn.server.services.JobLevelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.server.datn.server.common.constants.AppConstants.*;

@Service
public class JobLevelServiceImpl implements JobLevelService {
    private final JobLevelRepository jobLevelRepository;

    public JobLevelServiceImpl(JobLevelRepository jobLevelRepository) {
        this.jobLevelRepository = jobLevelRepository;
    }

    @Override
    public JobLevel createJobLevel(JobRequest jobRequest) {
        JobLevel jobLevel = (JobLevel) AppUtils.converToEntities(jobRequest, JobLevel.class);
        Integer maxLevel = jobLevelRepository.findJobLevelByMaxLevel();
        jobLevel.setLevel(Objects.isNull(maxLevel) ? 1 : maxLevel);
        return jobLevelRepository.save(jobLevel);
    }

    @Override
    public BaseResult updateJobLevel(JobRequest jobRequest, String id) {
        BaseResult result = new BaseResult();
        String status = STATUS_U2;
        boolean exists = jobLevelRepository.existsById(id);
        if (exists) {
            status = STATUS_U0;
            JobLevel jobLevelOld = jobLevelRepository.getReferenceById(id);
            AppUtils.copyNonNullProperties(jobRequest, jobLevelOld);
            JobLevel jobLevel = jobLevelRepository.save(jobLevelOld);
            result.setResult(jobLevel);
        }
        result.setStatus(status);
        return result;
    }

    @Override
    public JobLevel findJobLevelById(String id) {
        return jobLevelRepository.findById(id).orElse(null);
    }

    @Override
    public String deleteLevel(String id) {
        String status = STATUS_D2;
        boolean exists = jobLevelRepository.existsById(id);
        if (exists){
            JobLevel jobLevel = jobLevelRepository.getReferenceById(id);
            jobLevelRepository.delete(jobLevel);
            status = STATUS_D0;
        }
        return status;
    }

    @Override
    public Page<JobLevel> findAllJobLevel(Pageable pageable) {
        return jobLevelRepository.findAllByOrderByLevelDesc(pageable);
    }
}
