package com.server.datn.server.services.impl;

import com.server.datn.server.common.constants.QueryConstants;
import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.manager.WorkingTimeRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.manager.WorkingTime;
import com.server.datn.server.entity.manager.WorkingTimeEmployee;
import com.server.datn.server.entity.user.Employee;
import com.server.datn.server.repositories.WorkingTimeRepository;
import com.server.datn.server.services.EmployeeService;
import com.server.datn.server.services.WorkingTimeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.server.datn.server.common.constants.AppConstants.*;

@Service
public class WorkingTimeImpl implements WorkingTimeService {
    private final WorkingTimeRepository workingTimeRepository;
    private final EmployeeService employeeService;
    private final EntityManager entityManager;

    public WorkingTimeImpl(WorkingTimeRepository workingTimeRepository, EmployeeService employeeService, EntityManager entityManager) {
        this.workingTimeRepository = workingTimeRepository;
        this.employeeService = employeeService;
        this.entityManager = entityManager;
    }

    @Override
    public BaseResult checkIn(WorkingTimeRequest check) {
        boolean isCheckIn = false;
        BaseResult result = new BaseResult();
        String status = FAIL_CODE;
        String employeeId = AppUtils.getCurrentUserId();
        Timestamp beginTime = AppUtils.getCurrentTimeBeginDay();
        Timestamp endTime = AppUtils.getCurrentTimeEndDay();
        List<WorkingTime> checkList =
                workingTimeRepository.findCheckInCheckOutByEmployeeIdAndTypeAndTimeIsBetweenOrderByTimeAsc(employeeId, CHECK_IN , beginTime, endTime);
        if(CollectionUtils.isEmpty(checkList)){
            isCheckIn = true;
        }
        status = saveAndGetStatus(isCheckIn, check, CHECK_IN, result, status);
        result.setStatus(status);
        return result;
    }

    @Override
    public BaseResult checkOut(WorkingTimeRequest check) {
        boolean isCheckOut = false;
        BaseResult result = new BaseResult();
        String status = FAIL_CODE;
        String employeeId = AppUtils.getCurrentUserId();
        Timestamp beginTime = AppUtils.getCurrentTimeBeginDay();
        Timestamp endTime = AppUtils.getCurrentTimeEndDay();
        List<WorkingTime> checkList =
                workingTimeRepository.findCheckInCheckOutByEmployeeIdAndTypeAndTimeIsBetweenOrderByTimeAsc(employeeId, CHECK_IN, beginTime, endTime);
        if(!CollectionUtils.isEmpty(checkList)){
            isCheckOut = true;
        }
        status = saveAndGetStatus(isCheckOut, check, CHECK_OUT, result, status);
        result.setStatus(status);
        return result;
    }

    private String saveAndGetStatus(boolean isCheck, WorkingTimeRequest check, String type, BaseResult result, String status) {
        if(isCheck){
            WorkingTime checkOut = (WorkingTime) AppUtils.converToEntities(check, WorkingTime.class);
            checkOut.setEmployeeId(AppUtils.getCurrentUserId());
            checkOut.setType(type);
            WorkingTime resultCheckOut = workingTimeRepository.save(checkOut);
            result.setResult(resultCheckOut);
            status = SUCC_CODE;
        }
        return status;
    }

    @Override
    public List<WorkingTime> findCheckInCheckOutByEmployeeIdAndTime(WorkingTimeRequest check, Pageable pageable) throws ParseException {
        String employeeId = check.getEmployeeId();
        String beginTimeStr = check.getFromDate() + " 00:00:00";
        String endTimeStr = check.getToDate() + " 23:59:59";
        Timestamp begin = TimeUtils.parseTimestamp(beginTimeStr);
        Timestamp end = TimeUtils.parseTimestamp(endTimeStr);

        Query query = entityManager.createNamedQuery(QueryConstants.WORKINGTIME_findCheckInCheckOutByEmployeeId, WorkingTime.class);
        query.setParameter("employeeId", employeeId);
        query.setParameter("fromDate", begin);
        query.setParameter("toDate", end);
        return query.getResultList();
    }


}
