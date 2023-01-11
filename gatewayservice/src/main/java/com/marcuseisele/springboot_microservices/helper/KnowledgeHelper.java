package com.server.datn.server.helper;

import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.base.ResponseData;
import com.server.datn.server.common.dto.base.ResponseResult;
import com.server.datn.server.common.dto.employee.KnowledgeRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.PagingUtils;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.entity.knowledge.Knowledge;
import com.server.datn.server.entity.knowledge.KnowledgeCategory;
import com.server.datn.server.services.KnowledgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.server.datn.server.common.constants.AppConstants.FAIL_CODE;
import static com.server.datn.server.common.constants.AppConstants.SUCC_CODE;
import static com.server.datn.server.common.constants.MessageConstants.*;

@Service
public class KnowledgeHelper {
    private final Logger logger = LoggerFactory.getLogger(LeaveCategoryHelper.class);
    private final KnowledgeService knowledgeService;

    private final Response response = new Response();

    public KnowledgeHelper(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    public Response createKnowledgeCategory(KnowledgeRequest knowledgeRequest){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            KnowledgeCategory knowledgeCategory = knowledgeService.createKnowledgeCategory(knowledgeRequest);
            Object result = AppUtils.converToDTO(knowledgeCategory, KnowledgeRequest.class);
            responseData.setData(result);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC, SUCC_CODE);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createKnowledgeCategory()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }

    public Response findKnowledgeCategory(Map<String, String> map){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            String name = map.get("name");
            List<KnowledgeCategory> knowledgeCategory;
            if(Objects.nonNull(name)){
                knowledgeCategory = knowledgeService.findKnowledgeCategoryByName(name);
            }else {
                knowledgeCategory = knowledgeService.findAllKnowledgeCategory();
            }
            Object result = AppUtils.converToDTO(knowledgeCategory, KnowledgeRequest[].class);
            responseData.setData(result);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createKnowledgeCategory()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }



    public Response createKnowledge(KnowledgeRequest knowledgeRequest){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Knowledge knowledge = knowledgeService.createKnowledge(knowledgeRequest);
            Object result = AppUtils.converToDTO(knowledge, KnowledgeRequest.class);
            responseData.setData(result);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC, SUCC_CODE);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createKnowledge()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }

    public Response findKnowledge(Map<String, String> map, Pageable pageable){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Page<Knowledge> knowledgeCategory = knowledgeService.findAllKnowledge(pageable);
            Object result = AppUtils.converToDTO(knowledgeCategory.getContent(), KnowledgeRequest[].class);
            PagingUtils.setDataResponse(responseData, knowledgeCategory);
            responseData.setData(result);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createKnowledgeCategory()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }

    public Response findKnowledge(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Knowledge knowledge = knowledgeService.findKnowledgeById(id);
            KnowledgeRequest result = (KnowledgeRequest) AppUtils.converToDTO(knowledge, KnowledgeRequest.class);
            KnowledgeRequest category = (KnowledgeRequest) AppUtils.converToDTO(knowledge.getKnowledgeCategory(), KnowledgeRequest.class);
            if(Objects.nonNull(result)){
                result.setCategory(category);
            }
            responseData.setData(result);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createKnowledgeCategory()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }

    public Response deleteCategory(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            String knowledge = knowledgeService.deleteCategoryById(id);
            responseResult = ResponseResultUtils.getResponseResult(knowledge);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createKnowledgeCategory()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }

    public Response deleteKnowledge(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            String category = knowledgeService.deleteKnowledgeById(id);
            responseResult = ResponseResultUtils.getResponseResult(category);
        }catch (Exception e){
            logger.error("*************CompanyHelper ERROR createKnowledgeCategory()*****************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }


}
