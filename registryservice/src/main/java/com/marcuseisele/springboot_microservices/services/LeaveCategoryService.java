package com.server.datn.server.services;

import com.server.datn.server.common.dto.company.TakeLeaveRequest;
import com.server.datn.server.entity.manager.LeaveCategory;
import com.server.datn.server.entity.manager.WorkFlowTakeLeave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public interface LeaveCategoryService {
    LeaveCategory createLeaveCategory(LeaveCategory leaveCategory);
    LeaveCategory findLeaveCategoryById(String id);
    List<LeaveCategory> findAllLeaveCategory();
    WorkFlowTakeLeave creatTakeLeave(TakeLeaveRequest takeLeaveRequest) throws ParseException;
    WorkFlowTakeLeave updateTakeLeave(String id, String status) throws ParseException, IOException;
    Page<WorkFlowTakeLeave> findWFTakeLeaveByReportTo(String reportTo,Timestamp begin, Timestamp end, Pageable pageable);
    Page<WorkFlowTakeLeave> findWFTakeLeaveInMonth(String employeeId, Timestamp begin, Timestamp end, Pageable pageable);
    List<WorkFlowTakeLeave> findWFTakeLeaveInMonth(Timestamp begin, Timestamp end);

}
