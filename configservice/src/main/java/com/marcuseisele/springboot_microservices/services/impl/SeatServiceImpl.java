package com.server.datn.server.services.impl;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.company.SeatRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.entity.map.Seat;
import com.server.datn.server.entity.user.Employee;
import com.server.datn.server.repositories.SeatRepository;
import com.server.datn.server.services.EmployeeService;
import com.server.datn.server.services.SeatService;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.server.datn.server.common.constants.AppConstants.STATUS_U0;
import static com.server.datn.server.common.constants.AppConstants.STATUS_U2;

@Service
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final EmployeeService employeeService;

    public SeatServiceImpl(SeatRepository seatRepository, EmployeeService employeeService) {
        this.seatRepository = seatRepository;
        this.employeeService = employeeService;
    }


    @Override
    public BaseResult updateSeat(SeatRequest seatRequest, String id) {
        BaseResult result = new BaseResult();
        String status = STATUS_U2;
        boolean exists = seatRepository.existsById(id);
        if (exists) {
            status = STATUS_U0;
            Seat seatOld = seatRepository.getReferenceById(id);
            AppUtils.copyNonNullProperties(seatRequest, seatOld);
            String employeeId = seatRequest.getEmployeeId();
            if (Objects.nonNull(employeeId)) {
                Employee employee = employeeService.findEmployeeById(employeeId);
                seatOld.setEmployee(employee);
            }
            Seat seat = seatRepository.save(seatOld);
            result.setResult(seat);
        }
        result.setStatus(status);
        return result;
    }
}
