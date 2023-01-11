package com.server.datn.server.helper;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.base.ResponseData;
import com.server.datn.server.common.dto.base.ResponseResult;
import com.server.datn.server.common.dto.job.JobRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.PagingUtils;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.entity.job.JobLevel;
import com.server.datn.server.entity.job.JobTitle;
import com.server.datn.server.services.JobLevelService;
import com.server.datn.server.services.JobTitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.server.datn.server.common.constants.AppConstants.FAIL_CODE;
import static com.server.datn.server.common.constants.AppConstants.SUCC_CODE;
import static com.server.datn.server.common.constants.MessageConstants.*;

@Service
public class JobLevelHelper {
    private final Logger logger = LoggerFactory.getLogger(JobLevelHelper.class);
    private final JobLevelService jobLevelService;
    private final JobTitleService jobTitleService;

    private final Response response = new Response();

    public JobLevelHelper(JobLevelService jobLevelService, JobTitleService jobTitleService) {
        this.jobLevelService = jobLevelService;
        this.jobTitleService = jobTitleService;
    }

    public Response createJobLevel(JobRequest jobRequest){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            JobLevel jobLevel = jobLevelService.createJobLevel(jobRequest);
            Object jobLevelResponse =  AppUtils.converToDTO(jobLevel, JobLevel.class);
            responseData.setData(jobLevelResponse);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC,SUCC_CODE);
        }catch (Exception e){
            logger.error("*******************JobLevelHelper ERROR createJobLevel()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response updateJobLevel(JobRequest jobRequest, String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            BaseResult result = jobLevelService.updateJobLevel(jobRequest, id);
            Object jobLevelResponse =  AppUtils.converToDTO(result.getResult(), JobLevel.class);
            responseResult = ResponseResultUtils.getResponseResult(result.getStatus());
            responseData.setData(jobLevelResponse);
        }catch (Exception e){
            logger.error("*******************JobLevelHelper ERROR createJobLevel()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response deleteJobTitle(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            String status = jobTitleService.deleteJobTitle(id);
            responseResult = ResponseResultUtils.getResponseResult(status);
        }catch (Exception e){
            logger.error("*******************JobLevelHelper ERROR createJobTitle()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(DELETE_FAIL_REFERENCE,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response findJobLevel(Map<String, String> map, Pageable pageable){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Page<JobLevel> jobLevels = jobLevelService.findAllJobLevel(pageable);
            Object officeResponse =  AppUtils.converToDTO(jobLevels.getContent(), JobLevel[].class);
            responseData.setData(officeResponse);
            PagingUtils.setDataResponse(responseData, jobLevels);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY,SUCC_CODE);
        }catch (Exception e){
            logger.error("*******************JobLevelHelper ERROR createJobTitle()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response findJobLevelById(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            JobLevel jobLevels = jobLevelService.findJobLevelById(id);
            Object officeResponse =  AppUtils.converToDTO(jobLevels, JobLevel.class);
            responseData.setData(officeResponse);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY,SUCC_CODE);
        }catch (Exception e){
            logger.error("*******************JobLevelHelper ERROR createJobTitle()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }



    public Response createJobTitle(JobRequest jobRequest){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            JobTitle jobTitle = jobTitleService.createJobTitle(jobRequest);
            Object officeResponse =  AppUtils.converToDTO(jobTitle, JobTitle.class);
            responseData.setData(officeResponse);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC,SUCC_CODE);
        }catch (Exception e){
            logger.error("*******************JobLevelHelper ERROR createJobTitle()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response updateJobTitle(JobRequest jobRequest, String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            BaseResult result = jobTitleService.updateJobTitle(jobRequest, id);
            Object jobTitleResponse =  AppUtils.converToDTO(result.getResult(), JobTitle.class);
            responseData.setData(jobTitleResponse);
            responseResult = ResponseResultUtils.getResponseResult(result.getStatus());
        }catch (Exception e){
            logger.error("*******************JobLevelHelper ERROR createJobTitle()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(UPDATE_FAIL,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response deleteLevel(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            String status = jobLevelService.deleteLevel(id);
            responseResult = ResponseResultUtils.getResponseResult(status);
        }catch (Exception e){
            logger.error("*******************JobLevelHelper ERROR createJobTitle()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(DELETE_FAIL_REFERENCE, FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response findJobTitle(Map<String, String> map, Pageable pageable){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Page<JobTitle> jobTitles = jobTitleService.findAllJobTitle(pageable);
            Object officeResponse =  AppUtils.converToDTO(jobTitles.getContent(), JobTitle[].class);
            responseData.setData(officeResponse);
            PagingUtils.setDataResponse(responseData, jobTitles);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY,SUCC_CODE);
        }catch (Exception e){
            logger.error("*******************JobLevelHelper ERROR createJobTitle()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response findJobTitleById(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            JobTitle jobTitles = jobTitleService.findJobTitleById(id);
            Object officeResponse =  AppUtils.converToDTO(jobTitles, JobTitle.class);
            responseData.setData(officeResponse);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY,SUCC_CODE);
        }catch (Exception e){
            logger.error("*******************JobLevelHelper ERROR createJobTitle()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }


}
