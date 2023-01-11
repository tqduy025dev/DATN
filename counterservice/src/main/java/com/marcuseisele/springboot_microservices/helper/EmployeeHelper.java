package com.server.datn.server.helper;

import com.server.datn.server.authority.MyUserDetail;
import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.base.ResponseData;
import com.server.datn.server.common.dto.base.ResponseResult;
import com.server.datn.server.common.dto.employee.EmployeeRequest;
import com.server.datn.server.common.dto.employee.EmployeeResponse;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.PagingUtils;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.entity.user.Employee;
import com.server.datn.server.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.server.datn.server.common.constants.AppConstants.FAIL_CODE;
import static com.server.datn.server.common.constants.AppConstants.SUCC_CODE;
import static com.server.datn.server.common.constants.MessageConstants.*;

@Service
public class EmployeeHelper {
    private final Logger logger = LoggerFactory.getLogger(EmployeeHelper.class);

    private final EmployeeService employeeService;

    private final Response response = new Response();

    public EmployeeHelper(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    public Response createEmployee(EmployeeRequest employeeRequest) {
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Employee result = employeeService.createEmployee(employeeRequest);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC, SUCC_CODE);
            EmployeeResponse employeeResponse = AppUtils.getEmployeeResponse(result);
            responseData.setData(employeeResponse);
        } catch (Exception e) {
            logger.error("******EmployeeHelper Error createEmployee()******", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL, FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    public Response updateEmployee(EmployeeRequest employeeRequest, String id) {
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            BaseResult result = employeeService.updateEmployee(employeeRequest, id);
            responseResult = ResponseResultUtils.getResponseResult(result.getStatus());
            Object employeeResponse = AppUtils.getEmployeeResponse((Employee) result.getResult());
            responseData.setData(employeeResponse);
        } catch (Exception e) {
            logger.error("******EmployeeHelper Error updateEmployee()******", e);
            responseResult = ResponseResultUtils.getResponseResult(UPDATE_FAIL, FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    public Response deleteEmployee(String id) {
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            String result = employeeService.deleteEmployeeById(id);
            responseResult = ResponseResultUtils.getResponseResult(result);
        } catch (Exception e) {
            logger.error("******EmployeeHelper Error deleteEmployee()******", e);
            responseResult = ResponseResultUtils.getResponseResult(DELETE_FAIL, FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    public Response findEmployeeLevel(String level){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            List<EmployeeResponse> employeeResponses = new ArrayList<>();

            boolean isNumeric = AppUtils.isNumeric(level);
            if(isNumeric){
                int levelNum = Integer.parseInt(level);
                List<Employee> result = employeeService.findEmployeeByLevel(levelNum);
                result.forEach(employee -> {
                    EmployeeResponse employeeResponse = AppUtils.getEmployeeResponse(employee);
                    employeeResponses.add(employeeResponse);
                });
                responseData.setData(employeeResponses);
                responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
            }else {
                Employee em = employeeService.findEmployeeById(level);
                if(Objects.nonNull(em)){
                    String reportToId = Objects.nonNull(em.getReportTo()) ? em.getReportTo().getEmployeeId() : em.getEmployeeId();
                    List<Employee> result = employeeService.findEmployeeByLevel(reportToId);
                    result.forEach(employee -> {
                        EmployeeResponse employeeResponse = AppUtils.getEmployeeResponse(employee);
                        employeeResponses.add(employeeResponse);
                    });
                    Employee main = employeeService.findEmployeeById(reportToId);
                    EmployeeResponse mainEmResponse = AppUtils.getEmployeeResponse(main);
                    if(Objects.nonNull(mainEmResponse)){
                        mainEmResponse.setChildrens(employeeResponses);
                        responseData.setData(mainEmResponse);
                        responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
                    }else {
                        responseResult = ResponseResultUtils.getResponseResult(NOT_FOUND_REPORT_TO, FAIL_CODE);
                    }
                }else {
                    responseResult = ResponseResultUtils.getResponseResult(NOT_FOUND_REPORT_TO, FAIL_CODE);
                }
            }

        } catch (RuntimeException e){
            logger.error("******EmployeeHelper Error findEmployeeLevel()******", e);
            responseResult = ResponseResultUtils.getResponseResult(NOT_FOUND_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;

    }


    public Response findEmployee(Map<String, String> map, Pageable pageable) {
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            List<EmployeeResponse> employeeResponses = new ArrayList<>();
            Page<Employee> result = employeeService.findEmployeeByFields(map, pageable);
            result.forEach(employee -> {
                EmployeeResponse employeeResponse = AppUtils.getEmployeeResponse(employee);
                employeeResponses.add(employeeResponse);
            });
            PagingUtils.setDataResponse(responseData, result);
            responseData.setData(employeeResponses);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
        } catch (ParseException e) {
            logger.error("******EmployeeHelper Error findEmployee()******", e);
            responseResult = ResponseResultUtils.getResponseResult(PARAMETER_INVALID, FAIL_CODE);
            responseData = null;
        } catch (RuntimeException e) {
            logger.error("******EmployeeHelper Error findEmployee()******", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    public Response findEmployeeById(String id) {
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Employee employee = employeeService.findEmployeeById(id);
            if (Objects.nonNull(employee)) {
                Object employeeResponse = AppUtils.getEmployeeResponse(employee);
                responseData.setData(employeeResponse);
                responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
            } else {
                responseResult = ResponseResultUtils.getResponseResult(NOT_FOUND_KEY, FAIL_CODE);
            }
        } catch (Exception e) {
            logger.error("******EmployeeHelper Error findEmployeeById()******", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }


    public Response authorizeEmployee(Authentication authentication) {
        ResponseResult responseResult = null;
        ResponseData responseData = new ResponseData();
        try {
            if (authentication.isAuthenticated()) {
                MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
                Employee employee = myUserDetail.getEmployee();
                EmployeeResponse employeeResponse = AppUtils.getEmployeeResponse(employee);
                responseData.setData(employeeResponse);
                responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
            }
        } catch (Exception e) {
            logger.error("******EmployeeHelper Error authorizeEmployee()******", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
        }
        response.setResult(responseResult);
        response.setResponse(responseData);
        return response;
    }


}
