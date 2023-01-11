package com.server.datn.server.helper;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.base.ResponseData;
import com.server.datn.server.common.dto.base.ResponseResult;
import com.server.datn.server.common.dto.company.OfficeRequest;
import com.server.datn.server.common.dto.company.OfficeResponse;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.PagingUtils;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.entity.company.Office;
import com.server.datn.server.services.OfficeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

import static com.server.datn.server.common.constants.AppConstants.FAIL_CODE;
import static com.server.datn.server.common.constants.AppConstants.SUCC_CODE;
import static com.server.datn.server.common.constants.MessageConstants.*;

@Service
public class OfficeHelper {
    private final Logger logger = LoggerFactory.getLogger(LoginHelper.class);

    private final OfficeService officeService;
    private final Response response = new Response();


    public OfficeHelper(OfficeService officeService) {
        this.officeService = officeService;
    }

    public Response createOffice(OfficeRequest officeRequest){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Office office = officeService.createOffice(officeRequest);
            Object officeResponse =  AppUtils.converToDTO(office, OfficeResponse.class);
            responseData.setData(officeResponse);

            responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC,SUCC_CODE);
        }catch (Exception e){
            logger.error("*******************OfficeHelper ERROR createOffice()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response updateOffice(OfficeRequest officeRequest, String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            BaseResult result = officeService.updateOffice(officeRequest, id);
            responseResult = ResponseResultUtils.getResponseResult(result.getStatus());
            Object officeResponse =  AppUtils.converToDTO(result.getResult(), OfficeResponse.class);
            responseData.setData(officeResponse);
        }catch (Exception e){
            logger.error("*******************OfficeHelper ERROR createOffice()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response deleteOffice(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            String result = officeService.deleteOffice(id);
            responseResult = ResponseResultUtils.getResponseResult(result);
        }catch (Exception e){
            logger.error("*******************OfficeHelper ERROR deleteOffice()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }


    public Response findOffice(Map<String, String> map, Pageable pageable, boolean isPaging){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Page<Office> offices = officeService.findOfficeByFields(map,pageable);
            Object officeResponses = AppUtils.converToDTO(offices.getContent(), OfficeResponse[].class);
            if(isPaging){
                PagingUtils.setDataResponse(responseData, offices);
            }
            responseData.setData(officeResponses);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY,SUCC_CODE);
        }catch (Exception e){
            logger.error("*******************OfficeHelper ERROR findOffice()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response findOfficeById(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Office office = officeService.findOfficeById(id);
            if(Objects.nonNull(office)){
                Object officeResponses = AppUtils.converToDTO(office, OfficeResponse.class);
                responseData.setData(officeResponses);
                responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY,SUCC_CODE);
            }else {
                responseResult = ResponseResultUtils.getResponseResult(NOT_FOUND_KEY,FAIL_CODE);
            }
        }catch (Exception e){
            logger.error("*******************OfficeHelper ERROR findOfficeById()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

}
