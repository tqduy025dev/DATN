package com.server.datn.server.common.utils;

import com.server.datn.server.common.constants.AppConstants;
import com.server.datn.server.common.dto.base.ResponseResult;

import static com.server.datn.server.common.constants.AppConstants.*;
import static com.server.datn.server.common.constants.MessageConstants.*;

public class ResponseResultUtils {

    public static ResponseResult getResponseResult(String key, String code) {
        ResponseResult responseResult = new ResponseResult();
        String description = ResourceBundle.getInstance().getName(key);
        responseResult.setKey(key);
        responseResult.setCode(code);
        responseResult.setDesc(description);
        switch (code) {
            case FAIL_CODE:
                responseResult.setHttpStatus(404);
                break;
            case UNAUTHORIZED_CODE:
                responseResult.setHttpStatus(401);
                break;
            case BAD_REQUEST_CODE:
                responseResult.setHttpStatus(400);
                break;
            case CONFLICT_CODE:
                responseResult.setHttpStatus(409);
                break;
            case CREATED_CODE:
                responseResult.setHttpStatus(201);
                break;
            case ACCEPTED_CODE:
                responseResult.setHttpStatus(202);
                break;
            default:
                responseResult.setHttpStatus(200);
        }
        return responseResult;
    }

    public static ResponseResult getResponseResult(String result) {
        ResponseResult responseResult;
        switch (result) {
            case STATUS_D0:
                responseResult = ResponseResultUtils.getResponseResult(DELETE_SUCC, SUCC_CODE);
                break;
            case STATUS_D1:
                responseResult = ResponseResultUtils.getResponseResult(DELETE_FAIL_DELETED, FAIL_CODE);
                break;
            case STATUS_D2:
                responseResult = ResponseResultUtils.getResponseResult(DELETE_FAIL_NOT_EXISTS, FAIL_CODE);
                break;
            case STATUS_U0:
                responseResult = ResponseResultUtils.getResponseResult(UPDATE_SUCC, SUCC_CODE);
                break;
            case STATUS_U2:
                responseResult = ResponseResultUtils.getResponseResult(UPDATE_FAIL_NOT_EXISTS, FAIL_CODE);
                break;
            case SUCC_CODE:
                responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
                break;
            case STATUS_C1:
                responseResult = ResponseResultUtils.getResponseResult(INSUFFICIENT_LEAVE_DAYS, FAIL_CODE);
                break;
            case PASSWORD_UNVALID:
                responseResult = ResponseResultUtils.getResponseResult(PASSWORD_UNVALID_KEY, FAIL_CODE);
                break;
            case PASSWORD_NOT_MATCH:
                responseResult = ResponseResultUtils.getResponseResult(PASSWORD_NOT_MATCH_KEY, FAIL_CODE);
                break;

            default:
                responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
                break;
        }
        return responseResult;
    }

    public static String getCompanyCode() {
        return ResourceBundle.getInstance().getName(COMPANY_KEY);
    }

}
