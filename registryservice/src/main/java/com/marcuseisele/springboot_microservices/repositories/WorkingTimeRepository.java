package com.server.datn.server.repositories;

import com.server.datn.server.entity.manager.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface WorkingTimeRepository extends JpaRepository<WorkingTime, String> {
    List<WorkingTime> findCheckInCheckOutByEmployeeIdAndTypeAndTimeIsBetweenOrderByTimeAsc(String employeeId, String type, Timestamp begin, Timestamp end);
}
