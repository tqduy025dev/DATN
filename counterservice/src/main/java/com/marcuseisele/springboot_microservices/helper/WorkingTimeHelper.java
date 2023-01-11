package com.server.datn.server.helper;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.base.ResponseData;
import com.server.datn.server.common.dto.base.ResponseResult;
import com.server.datn.server.common.dto.employee.EmployeeResponse;
import com.server.datn.server.common.dto.manager.WorkingTimeRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.PagingUtils;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.company.Company;
import com.server.datn.server.entity.manager.WorkFlowTakeLeave;
import com.server.datn.server.entity.manager.WorkingTime;
import com.server.datn.server.entity.manager.WorkingTimeEmployee;
import com.server.datn.server.entity.user.Employee;
import com.server.datn.server.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import static com.server.datn.server.common.constants.AppConstants.*;
import static com.server.datn.server.common.constants.MessageConstants.*;

@Service
public class WorkingTimeHelper {
    private final Logger logger = LoggerFactory.getLogger(WorkingTimeHelper.class);
    private final WorkingTimeService workingTimeService;
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final LeaveCategoryService leaveCategoryService;


    private final Response response = new Response();
    private final String companyCode = ResponseResultUtils.getCompanyCode();
    private List<Integer> workDayOfWeek = new ArrayList<>();

    public WorkingTimeHelper(WorkingTimeService workingTimeService, EmployeeService employeeService, CompanyService companyService, LeaveCategoryService leaveCategoryService) {
        this.workingTimeService = workingTimeService;
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.leaveCategoryService = leaveCategoryService;
    }

    public Response checkIn(WorkingTimeRequest check) {
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            BaseResult checkIn = workingTimeService.checkIn(check);
            Object result = AppUtils.getCheckInCheckOut((WorkingTime) checkIn.getResult());
            responseData.setData(result);
            responseResult = ResponseResultUtils.getResponseResult(checkIn.getStatus());
        } catch (Exception e) {
            logger.error("******CheckInCheckOutHelper Error checkIn()******", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }

        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    public Response checkOut(WorkingTimeRequest check) {
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            BaseResult checkOut = workingTimeService.checkOut(check);
            Object result = AppUtils.getCheckInCheckOut((WorkingTime) checkOut.getResult());
            responseData.setData(result);
            responseResult = ResponseResultUtils.getResponseResult(checkOut.getStatus());
        } catch (Exception e) {
            logger.error("******CheckInCheckOutHelper Error checkOut()******", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }

        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }


    public Response findCheckInCheckOut(WorkingTimeRequest check, Pageable pageable) {
        Company company = companyService.findCompanyByCode(companyCode);
        workDayOfWeek = company.getWorkDayOfWeek();
        ResponseResult responseResult;
        ResponseData responseData = new ResponseData();
        try {
            Timestamp begin = TimeUtils.parseDateTimestamp(check.getFromDate());
            Timestamp end = TimeUtils.parseDateTimestamp(check.getToDate());
            List<WorkFlowTakeLeave> workFlowTakeLeaves = leaveCategoryService.findWFTakeLeaveInMonth(begin, end);

            String employeeId = check.getEmployeeId();
            if (Objects.isNull(employeeId)) {
                List<WorkingTimeEmployee> result = new ArrayList<>();
                Page<Employee> employees = employeeService.findAllEmployee(pageable);
                for (Employee employee : employees) {
                    check.setEmployeeId(employee.getEmployeeId());
                    List<WorkFlowTakeLeave> takeLeaveEmployees = workFlowTakeLeaves.stream().filter(i -> i.getCreateBy().equals(employee)).collect(Collectors.toList());

                    List<WorkingTimeRequest> workingTimeRequests = this.getWorkingTimeRequests(check, pageable, company);
                    Long totalWorkingDayThisMonth = workingTimeRequests.stream().filter(i -> !i.getDayOff()).count();
                    Long totalWorkingDay = workingTimeRequests.stream().filter(i -> Objects.nonNull(i.getTimeCheckIn()) || Objects.nonNull(i.getTimeCheckOut())).count();
                    removeWorkingTimeRequest(workingTimeRequests, check);

                    workingTimeRequests.forEach(i -> {
                        int leave = AppUtils.takeLeave(i, takeLeaveEmployees);
                        i.setTakeLeave(leave);
                    });

                    Long totalTakeLeave = workingTimeRequests.stream().filter(i -> i.getTakeLeave() == 1).count();
                    Long totalTakeOff = workingTimeRequests.stream().filter(i -> i.getTakeLeave() == 2).count();

                    EmployeeResponse employeeResponse = AppUtils.getEmployeeResponse(employee);
                    WorkingTimeEmployee workingTimeEmployee = new WorkingTimeEmployee();
                    workingTimeEmployee.setWorkingTime(workingTimeRequests);
                    workingTimeEmployee.setEmployee(employeeResponse);
                    workingTimeEmployee.setTotalWorkingDayThisMonth(totalWorkingDayThisMonth);
                    workingTimeEmployee.setTotalWorkingDay(totalWorkingDay);
                    workingTimeEmployee.setTotalTakeLeave(totalTakeLeave);
                    workingTimeEmployee.setTotalTakeOff(totalTakeOff);
                    result.add(workingTimeEmployee);
                }
                responseData.setData(result);
                PagingUtils.setDataResponse(responseData, employees);
                responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
            } else {
                boolean exist = employeeService.existEmployeeById(employeeId);
                if (!exist) {
                    responseResult = ResponseResultUtils.getResponseResult(NOT_FOUND_EMPLOYEE_KEY, FAIL_CODE);
                } else {
                    Employee employee = employeeService.findEmployeeById(employeeId);
                    EmployeeResponse employeeResponse = AppUtils.getEmployeeResponse(employee);
                    List<WorkFlowTakeLeave> takeLeaveEmployees = workFlowTakeLeaves.stream().filter(i -> i.getCreateBy().equals(employee)).collect(Collectors.toList());

                    List<WorkingTimeRequest> workingTimeRequests = this.getWorkingTimeRequests(check, pageable, company);
                    Long totalWorkingDayThisMonth = workingTimeRequests.stream().filter(i -> !i.getDayOff()).count();
                    Long totalWorkingDay = workingTimeRequests.stream().filter(i -> Objects.nonNull(i.getTimeCheckIn()) || Objects.nonNull(i.getTimeCheckOut())).count();
                    removeWorkingTimeRequest(workingTimeRequests, check);

                    workingTimeRequests.forEach(i -> {
                        int leave = AppUtils.takeLeave(i, takeLeaveEmployees);
                        i.setTakeLeave(leave);
                    });

                    Long totalTakeLeave = workingTimeRequests.stream().filter(i -> i.getTakeLeave() == 1).count();
                    Long totalTakeOff = workingTimeRequests.stream().filter(i -> i.getTakeLeave() == 2).count();

                    WorkingTimeEmployee workingTimeEmployee = new WorkingTimeEmployee();
                    workingTimeEmployee.setWorkingTime(workingTimeRequests);
                    workingTimeEmployee.setEmployee(employeeResponse);
                    workingTimeEmployee.setTotalWorkingDayThisMonth(totalWorkingDayThisMonth);
                    workingTimeEmployee.setTotalWorkingDay(totalWorkingDay);
                    workingTimeEmployee.setTotalTakeLeave(totalTakeLeave);
                    workingTimeEmployee.setTotalTakeOff(totalTakeOff);
                    responseData.setData(workingTimeEmployee);
                    responseResult = ResponseResultUtils.getResponseResult(SUCC_KEY, SUCC_CODE);
                }
            }

        } catch (Exception e) {
            logger.error("******CheckInCheckOutHelper Error checkOut()******", e);
            responseResult = ResponseResultUtils.getResponseResult(FAIL_KEY, FAIL_CODE);
            responseData = null;
        }

        response.setResponse(responseData);
        response.setResult(responseResult);
        return response;
    }

    private List<WorkingTimeRequest> getWorkingTimeRequests(WorkingTimeRequest check, Pageable pageable, Company company) throws ParseException {
        List<WorkingTime> workingTimes = workingTimeService.findCheckInCheckOutByEmployeeIdAndTime(check, pageable);
        Map<String, List<WorkingTime>> map = workingTimes.stream().collect(Collectors.groupingBy(i -> TimeUtils.parseStringDate(i.getTime())));
        List<WorkingTimeRequest> workingTimeRequests = AppUtils.getWorkingTimeRequestFromData(map, company);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtils.parseDate(check.getFromDate()));
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();

        for (int i = 1; i <= daysInMonth; i++) {
            String key = AppUtils.getKeyDate(i, month, year);
            if (!map.containsKey(key)) {
                WorkingTimeRequest workingTimeRequest = this.getDefaultWorkingTimeRequest(key, check.getEmployeeId());
                workingTimeRequests.add(workingTimeRequest);
            }
        }
        workingTimeRequests.sort(Comparator.comparing(WorkingTimeRequest::getCheckDate));
        return workingTimeRequests;
    }


    private WorkingTimeRequest getDefaultWorkingTimeRequest(String... key) throws ParseException {
        WorkingTimeRequest workingTimeRequest = new WorkingTimeRequest();
        workingTimeRequest.setCheckDate(key[0]);
        workingTimeRequest.setEmployeeId(key[1]);
        workingTimeRequest.setTotalTime("00:00:00");
        workingTimeRequest.setEarlyTime("00:00:00");
        workingTimeRequest.setLateTime("00:00:00");
        String keyHoliday = String.valueOf(key[0]);
        boolean isHoliday = AppUtils.getHoliday().contains(keyHoliday.replaceAll("/", ""));
        workingTimeRequest.setHoliday(isHoliday);
        Calendar calendar = TimeUtils.parseCalandar(key[0]);
        workingTimeRequest.setDayOff(!workDayOfWeek.contains(calendar.get(Calendar.DAY_OF_WEEK)));
        return workingTimeRequest;
    }



    private void removeWorkingTimeRequest(List<WorkingTimeRequest> workingTimeRequests, WorkingTimeRequest check) {
        workingTimeRequests.removeIf(i ->
        {
            try {
                return !(TimeUtils.parseDate(i.getCheckDate()).compareTo(TimeUtils.parseDate(check.getFromDate())) >= 0 &&
                        TimeUtils.parseDate(i.getCheckDate()).compareTo(TimeUtils.parseDate(check.getToDate())) <= 0);
            } catch (ParseException ignore) {
            }
            return false;
        });

    }

}
