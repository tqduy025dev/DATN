package com.server.datn.server.helper;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.base.ResponseData;
import com.server.datn.server.common.dto.base.ResponseResult;
import com.server.datn.server.common.dto.company.CompanyRequest;
import com.server.datn.server.common.dto.company.CompanyResponse;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.entity.company.Company;
import com.server.datn.server.entity.manager.LeaveCategory;
import com.server.datn.server.services.CompanyService;
import com.server.datn.server.services.LeaveCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.server.datn.server.common.constants.AppConstants.FAIL_CODE;
import static com.server.datn.server.common.constants.AppConstants.SUCC_CODE;
import static com.server.datn.server.common.constants.MessageConstants.*;

@Service
public class CompanyHelper {
    private final Logger logger = LoggerFactory.getLogger(CompanyHelper.class);

    private final CompanyService companyService;

    private final Response response = new Response();

    public CompanyHelper(CompanyService companyService) {
        this.companyService = companyService;
    }

    public Response createCompany(CompanyRequest companyRequest){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Company company = companyService.createCompany(companyRequest);
            Object companyResponse = AppUtils.converToDTO(company, CompanyResponse.class);
            responseData.setData(companyResponse);
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

    public Response updateCompany(CompanyRequest companyRequest, String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            BaseResult result = companyService.updateCompany(companyRequest, id);
            responseResult = ResponseResultUtils.getResponseResult(result.getStatus());
            Object companyResponse = AppUtils.converToDTO(result.getResult(), CompanyResponse.class);
            responseData.setData(companyResponse);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createEmployee()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }


    public Response findCompany(Map<String, String> map){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            String companyCode = ResponseResultUtils.getCompanyCode();
            Company company = companyService.findCompanyByCode(companyCode);
            if(Objects.nonNull(company)){
                Object companyResponse = AppUtils.converToDTO(company, CompanyResponse.class);
                responseData.setData(companyResponse);
                responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
            }else {
                responseData = null;
                responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            }
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
