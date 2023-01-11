package com.server.datn.server.services;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.employee.EmployeeRequest;
import com.server.datn.server.entity.user.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee createEmployee(EmployeeRequest employeeRequest) throws IOException, ParseException;
    BaseResult updateEmployee(EmployeeRequest employeeRequest, String id) throws ParseException, IOException;
    Employee findEmployeeByUserName(String username);
    Employee findEmployeeById(String id);
    boolean existEmployeeById(String id);
    String deleteEmployeeById(String id);
    Page<Employee> findEmployeeByFields(Map<String, String> map, Pageable pageable) throws ParseException;
    Page<Employee> findAllEmployee(Pageable pageable);
    List<Employee> findEmployeeByLevel(int level);
    List<Employee> findEmployeeByLevel(String id);
    Employee getEmployeeRef(String id);
}
