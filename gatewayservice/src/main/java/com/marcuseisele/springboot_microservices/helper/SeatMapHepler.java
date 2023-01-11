package com.server.datn.server.helper;

import com.server.datn.server.common.dto.base.*;
import com.server.datn.server.common.dto.company.SeatMapRequest;
import com.server.datn.server.common.dto.company.SeatMapResponse;
import com.server.datn.server.common.dto.company.SeatRequest;
import com.server.datn.server.common.dto.company.SeatResponse;
import com.server.datn.server.common.dto.manager.WorkingTimeRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.PagingUtils;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.company.Company;
import com.server.datn.server.entity.files.SystemFile;
import com.server.datn.server.entity.manager.WorkingTime;
import com.server.datn.server.entity.map.Seat;
import com.server.datn.server.entity.map.SeatMaps;
import com.server.datn.server.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.server.datn.server.common.constants.AppConstants.*;
import static com.server.datn.server.common.constants.MessageConstants.*;

@Service
public class SeatMapHepler {
    private final Logger logger = LoggerFactory.getLogger(SeatMapHepler.class);
    private final Response response = new Response();

    private final SeatMapService seatMapService;
    private final SeatService seatService;
    private final FileService fileService;
    private final WorkingTimeService workingTimeService;
    private final CompanyService companyService;
    private final String companyCode = ResponseResultUtils.getCompanyCode();


    public SeatMapHepler(SeatMapService seatMapService, SeatService seatService, FileService fileService, WorkingTimeService workingTimeService, CompanyService companyService) {
        this.seatMapService = seatMapService;
        this.seatService = seatService;
        this.fileService = fileService;
        this.workingTimeService = workingTimeService;
        this.companyService = companyService;
    }

    public Response createFile(MultipartFile file){
        ResponseResult responseResult;
        ResponseData responseData;
        try {
            SystemFile systemFile = fileService.createFile(file);
            responseData = this.getFileData(systemFile);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC,SUCC_CODE);
        }catch (Exception e){
            logger.error("******************SeatMapHepler ERROR createFile()******************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    public Response createSeatMap(SeatMapRequest seatMapRequest){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            SeatMaps seatMaps = seatMapService.createSeatMap(seatMapRequest);
            Object seatMapResponse = AppUtils.converToDTO(seatMaps, SeatMapResponse.class);
            responseData.setData(seatMapResponse);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_SUCC,SUCC_CODE);
        }catch (Exception e){
            logger.error("******************SeatMapHepler ERROR createSeatMap()******************", e);
            responseResult = ResponseResultUtils.getResponseResult(CREATE_FAIL,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    public Response findSeatMap(Map<String, String> map, Pageable pageable){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Object seatMapResponses;
            if(map.containsKey(FIELD_OFFICE_ID) || map.containsKey(FIELD_EMPLOYEE_ID)){
                WorkingTimeRequest workingTimeRequest = new WorkingTimeRequest();
                workingTimeRequest.setFromDate(TimeUtils.getStringDateNow());
                workingTimeRequest.setToDate(TimeUtils.getStringDateNow());
                Company company = companyService.findCompanyByCode(companyCode);

                SeatMaps seatMaps = seatMapService.findSeatMapByfields(map);
                seatMapResponses = AppUtils.converToDTO(seatMaps, SeatMapResponse.class);
                if(Objects.nonNull(seatMapResponses)){
                    for (SeatResponse seat : ((SeatMapResponse)seatMapResponses).getSeats()){
                        if(Objects.nonNull(seat.getEmployee())){
                            String employeeId = seat.getEmployee().getId();
                            workingTimeRequest.setEmployeeId(employeeId);
                            List<WorkingTime> workingTimes =  workingTimeService.findCheckInCheckOutByEmployeeIdAndTime(workingTimeRequest, pageable);
                            Map<String, List<WorkingTime>> mapGroup = workingTimes.stream().collect(Collectors.groupingBy(i -> TimeUtils.parseStringDate(i.getTime())));
                            List<WorkingTimeRequest> workingTimeRequests = AppUtils.getWorkingTimeRequestFromData(mapGroup, company);
                            if(!CollectionUtils.isEmpty(workingTimeRequests)){
                                seat.getEmployee().setWorkingTime(workingTimeRequests.get(0));
                            }
                        }
                    }
                }
            }else {
                Page<?> seatMaps = seatMapService.findAllSeatMap(pageable);
                seatMapResponses = AppUtils.converToDTO(seatMaps.getContent(), SeatMapResponse[].class);
                PagingUtils.setDataResponse(responseData, seatMaps);
            }
            responseData.setData(seatMapResponses);
            responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY,SUCC_CODE);
        }catch (Exception e){
            logger.error("*******************OfficeHelper ERROR createOffice()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response findSeatMapById(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            SeatMaps seatMap = seatMapService.findSeatMapById(id);
            if(Objects.nonNull(seatMap)){
                Object seatMapResponses = AppUtils.converToDTO(seatMap, SeatMapResponse.class);
                responseData.setData(seatMapResponses);
                responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY,SUCC_CODE);
            }else {
                responseResult = ResponseResultUtils.getResponseResult(NOT_FOUND_KEY, FAIL_CODE);
            }

        }catch (Exception e){
            logger.error("*******************OfficeHelper ERROR createOffice()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response updateSeatMap(SeatMapRequest seatMapRequest, String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            BaseResult result = seatMapService.updateSeatMap(seatMapRequest, id);
            responseResult = ResponseResultUtils.getResponseResult(result.getStatus());
            Object seatMapResponses = AppUtils.converToDTO(result.getResult(), SeatMapResponse.class);
            responseData.setData(seatMapResponses);

        }catch (Exception e){
            logger.error("*******************OfficeHelper ERROR createOffice()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    public Response deleteSeatMap(String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            String result = seatMapService.deleteSeatMap(id);
            responseResult = ResponseResultUtils.getResponseResult(result);
        }catch (Exception e){
            logger.error("*******************OfficeHelper ERROR createOffice()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }


    public Response updateSeat(SeatRequest seatRequest, String id){
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            BaseResult result = seatService.updateSeat(seatRequest, id);
            responseResult = ResponseResultUtils.getResponseResult(result.getStatus());
            Object seatMapResponses = AppUtils.converToDTO(result.getResult(), SeatResponse.class);
            responseData.setData(seatMapResponses);
        }catch (Exception e){
            logger.error("*******************OfficeHelper ERROR createOffice()*******************", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY,FAIL_CODE);
            responseData = null;
        }
        response.setResponse(responseData);
        response.setResult(responseResult);

        return response;
    }

    private ResponseData getFileData(SystemFile systemFile) {
        ResponseData responseData = new ResponseData();

        FileResponse file = new FileResponse();
        file.setFileId(systemFile.getFileId());
        file.setData(systemFile.getData());
        file.setType(systemFile.getType());
        responseData.setData(file);
        return responseData;
    }
}
