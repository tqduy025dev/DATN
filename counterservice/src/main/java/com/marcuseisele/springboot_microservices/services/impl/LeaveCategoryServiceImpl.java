package com.server.datn.server.services.impl;

import com.server.datn.server.common.constants.AppConstants;
import com.server.datn.server.common.dto.company.TakeLeaveRequest;
import com.server.datn.server.common.dto.employee.EmployeeRequest;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.manager.LeaveCategory;
import com.server.datn.server.entity.manager.WorkFlowTakeLeave;
import com.server.datn.server.entity.user.Employee;
import com.server.datn.server.repositories.LeaveCategoryRepository;
import com.server.datn.server.repositories.WorkFlowTakeLeaveRepository;
import com.server.datn.server.services.EmployeeService;
import com.server.datn.server.services.LeaveCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@Service
public class LeaveCategoryServiceImpl implements LeaveCategoryService {
    private final LeaveCategoryRepository leaveCategoryRepository;
    private final WorkFlowTakeLeaveRepository workFlowTakeLeaveRepository;
    private final EmployeeService employeeService;

    public LeaveCategoryServiceImpl(LeaveCategoryRepository leaveCategoryRepository, WorkFlowTakeLeaveRepository workFlowTakeLeaveRepository, EmployeeService employeeService) {
        this.leaveCategoryRepository = leaveCategoryRepository;
        this.workFlowTakeLeaveRepository = workFlowTakeLeaveRepository;
        this.employeeService = employeeService;
    }


    @Override
    public LeaveCategory createLeaveCategory(LeaveCategory leaveCategory) {
        return leaveCategoryRepository.save(leaveCategory);
    }

    @Override
    public LeaveCategory findLeaveCategoryById(String id) {
        return leaveCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<LeaveCategory> findAllLeaveCategory() {
        return leaveCategoryRepository.findAll();
    }

    @Override
    public WorkFlowTakeLeave creatTakeLeave(TakeLeaveRequest takeLeaveRequest) throws ParseException {
        WorkFlowTakeLeave workFlowTakeLeave = new WorkFlowTakeLeave();
        Employee createBy = employeeService.findEmployeeById(takeLeaveRequest.getEmployeeId());
        int daysOfLeaveRemaining = createBy.getDaysOfLeave();

        LeaveCategory category = this.findLeaveCategoryById(takeLeaveRequest.getCategoryId());

        if(!category.getPayable() || (daysOfLeaveRemaining > 0 && (daysOfLeaveRemaining - takeLeaveRequest.getDaysOfLeave()) >= 0)){
            Employee reportTo = createBy.getReportTo();

            workFlowTakeLeave.setCategory(category);
            workFlowTakeLeave.setReportTo(reportTo);
            workFlowTakeLeave.setCreateBy(createBy);
            workFlowTakeLeave.setTakeLeaveFrom(TimeUtils.parseDateTimestamp(takeLeaveRequest.getTakeLeaveFrom()));
            workFlowTakeLeave.setTakeLeaveTo(TimeUtils.parseDateTimestamp(takeLeaveRequest.getTakeLeaveTo()));
            workFlowTakeLeave.setDaysOfLeave(takeLeaveRequest.getDaysOfLeave());
            workFlowTakeLeave.setReason(takeLeaveRequest.getReason());

            return workFlowTakeLeaveRepository.save(workFlowTakeLeave);
        }
        return null;
    }

    @Override
    public WorkFlowTakeLeave updateTakeLeave(String id, String status) throws ParseException, IOException {
        boolean check = workFlowTakeLeaveRepository.existsById(id);
        if(check){
            WorkFlowTakeLeave workFlowTakeLeave = workFlowTakeLeaveRepository.getReferenceById(id);
            if(!AppConstants.STATUS_REJECT.equals(workFlowTakeLeave.getStatus())){
                boolean isContain = AppConstants.ACTIONS.contains(status);
                if(isContain){
                    workFlowTakeLeave.setStatus(status);
                    if(AppConstants.STATUS_APPROVE.equals(status)){
                        Employee employee = employeeService.getEmployeeRef(workFlowTakeLeave.getCreateBy().getEmployeeId());
                        EmployeeRequest employeeRequest = new EmployeeRequest();
                        int daysOfLeaveRemaining = employee.getDaysOfLeave() - workFlowTakeLeave.getDaysOfLeave();
                        employeeRequest.setDaysOfLeave(Math.max(daysOfLeaveRemaining, 0));
                        employeeService.updateEmployee(employeeRequest, workFlowTakeLeave.getCreateBy().getEmployeeId());
                    }
                }
            }
            return workFlowTakeLeaveRepository.save(workFlowTakeLeave);
        }
        throw new NullPointerException();
    }

    @Override
    public Page<WorkFlowTakeLeave> findWFTakeLeaveByReportTo(String reportTo,Timestamp begin, Timestamp end, Pageable pageable) {
        boolean check = employeeService.existEmployeeById(reportTo);
        if(check){
            Employee employee = employeeService.getEmployeeRef(reportTo);
            return workFlowTakeLeaveRepository.findByReportToAndCreatedTimeBetweenAndStatus(employee,begin, end, AppConstants.STATUS_PENDING , pageable);
        }
        return null;
    }

    @Override
    public Page<WorkFlowTakeLeave> findWFTakeLeaveInMonth(String employeeId, Timestamp begin, Timestamp end, Pageable pageable) {
        boolean check = employeeService.existEmployeeById(employeeId);
        if(check){
            Employee employee = employeeService.getEmployeeRef(employeeId);
            return workFlowTakeLeaveRepository.findByCreatedTimeBetweenAndCreateBy(begin, end, employee, pageable);
        }
        return null;
    }

    @Override
    public List<WorkFlowTakeLeave> findWFTakeLeaveInMonth(Timestamp begin, Timestamp end) {
        return workFlowTakeLeaveRepository.findByCreatedTimeBetweenAndStatus(begin, end,AppConstants.STATUS_APPROVE);
    }


}
