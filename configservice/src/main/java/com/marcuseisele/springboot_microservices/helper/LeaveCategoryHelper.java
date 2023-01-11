package com.server.datn.server.helper;

import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.base.ResponseData;
import com.server.datn.server.common.dto.base.ResponseResult;
import com.server.datn.server.common.dto.company.TakeLeaveRequest;
import com.server.datn.server.common.dto.company.TakeLeaveResponse;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.PagingUtils;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.manager.LeaveCategory;
import com.server.datn.server.entity.manager.WorkFlowTakeLeave;
import com.server.datn.server.services.LeaveCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.server.datn.server.common.constants.AppConstants.*;
import static com.server.datn.server.common.constants.MessageConstants.*;
import static com.server.datn.server.common.constants.MessageConstants.FAIL_KEY;

@Service
public class LeaveCategoryHelper {
    private final LeaveCategoryService leaveCategoryService;
    private final Logger logger = LoggerFactory.getLogger(LeaveCategoryHelper.class);
    private final Response response = new Response();

    public LeaveCategoryHelper(LeaveCategoryService leaveCategoryService) {
        this.leaveCategoryService = leaveCategoryService;
    }

    public Response createLeaveCategory(LeaveCategory leaveCategory){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            LeaveCategory category = leaveCategoryService.createLeaveCategory(leaveCategory);
            responseData.setData(category);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC, SUCC_CODE);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createEmployee()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }

    public Response findLeaveCategoryById(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            LeaveCategory category = leaveCategoryService.findLeaveCategoryById(id);
            responseData.setData(category);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createEmployee()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }

    public Response findAllLeaveCategory(){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            List<LeaveCategory> categorys = leaveCategoryService.findAllLeaveCategory();
            responseData.setData(categorys);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createEmployee()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }

    public Response createTakeLeave(TakeLeaveRequest takeLeaveRequest){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            WorkFlowTakeLeave takeLeave = leaveCategoryService.creatTakeLeave(takeLeaveRequest);
            if(Objects.nonNull(takeLeave)){
                Object takeLeaveResponse = AppUtils.converToDTO(takeLeave, TakeLeaveResponse.class);
                responseData.setData(takeLeaveResponse);
                responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC, SUCC_CODE);
            }else {
                responseResult = ResponseResultUtils.getResponseResult(STATUS_C1);
            }

        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createEmployee()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }

    public Response findTakeLeave(Map<String, String> map, Pageable pageable){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            String reportTo = map.get("reportTo");
            String employeeId = map.get("employeeId");
            String fromDateStr = map.get("fromDate");
            String toDateStr = map.get("toDate");
            Object takeLeaveResponse = null;
            if(Objects.nonNull(employeeId)){
                Timestamp fromDate = TimeUtils.parseDateTimestamp(fromDateStr);
                Timestamp toDate = TimeUtils.parseDateTimestamp(toDateStr);
                Page<WorkFlowTakeLeave> takeLeave = leaveCategoryService.findWFTakeLeaveInMonth(employeeId,fromDate,toDate, pageable);
                takeLeaveResponse = AppUtils.converToDTO(takeLeave.getContent(), TakeLeaveResponse[].class);
                PagingUtils.setDataResponse(responseData, takeLeave);
            }
            if(Objects.nonNull(reportTo)){
                Timestamp fromDate = TimeUtils.parseDateTimestamp(fromDateStr);
                Timestamp toDate = TimeUtils.parseDateTimestamp(toDateStr);
                Page<WorkFlowTakeLeave> takeLeave = leaveCategoryService.findWFTakeLeaveByReportTo(reportTo,fromDate,toDate, pageable);
                takeLeaveResponse = AppUtils.converToDTO(takeLeave.getContent(), TakeLeaveResponse[].class);
                PagingUtils.setDataResponse(responseData, takeLeave);
            }

            responseData.setData(takeLeaveResponse);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createEmployee()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }

    public Response updateTakeLeaveById(String id, String status){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            WorkFlowTakeLeave takeLeave = leaveCategoryService.updateTakeLeave(id, status);
            Object takeLeaveResponse = AppUtils.converToDTO(takeLeave, TakeLeaveResponse.class);
            responseData.setData(takeLeaveResponse);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createEmployee()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }




}
