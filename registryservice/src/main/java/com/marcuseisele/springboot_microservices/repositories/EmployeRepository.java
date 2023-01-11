package com.server.datn.server.repositories;

import com.server.datn.server.entity.user.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeRepository extends JpaRepository<Employee, String> {
    Employee findEmployeeByAccountUsername(String username);
    List<Employee> findEmployeeByJobLevelLevelIsGreaterThan(int level);
    List<Employee> findEmployeeByReportToEmployeeId(String id);
    Page<Employee> findAllByEmailNot(String email, Pageable pageable);
//    List<Employee> findEmployeeByJobLevelLevelIsGreaterThanAndJobLevelLevelIsLessThanOrderByJobLevelLevel(int begin, int end);
}
