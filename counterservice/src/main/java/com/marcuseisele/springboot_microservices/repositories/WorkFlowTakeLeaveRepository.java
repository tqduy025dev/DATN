package com.server.datn.server.repositories;

import com.server.datn.server.entity.manager.WorkFlowTakeLeave;
import com.server.datn.server.entity.user.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface WorkFlowTakeLeaveRepository extends JpaRepository<WorkFlowTakeLeave, String> {
    Page<WorkFlowTakeLeave> findByReportToAndCreatedTimeBetweenAndStatus(Employee reportTo,Timestamp begin, Timestamp end, String status, Pageable pageable);
    Page<WorkFlowTakeLeave> findByCreatedTimeBetweenAndCreateBy(Timestamp begin, Timestamp end, Employee em, Pageable pageable);
    List<WorkFlowTakeLeave> findByCreatedTimeBetweenAndStatus(Timestamp begin, Timestamp end, String status);
}
