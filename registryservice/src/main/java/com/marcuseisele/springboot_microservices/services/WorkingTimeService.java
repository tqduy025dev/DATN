package com.server.datn.server.services;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.manager.WorkingTimeRequest;
import com.server.datn.server.entity.manager.WorkingTime;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;

public interface WorkingTimeService {
    BaseResult checkIn(WorkingTimeRequest check);
    BaseResult checkOut(WorkingTimeRequest check);
    List<WorkingTime> findCheckInCheckOutByEmployeeIdAndTime(WorkingTimeRequest check, Pageable pageable) throws ParseException;
}
