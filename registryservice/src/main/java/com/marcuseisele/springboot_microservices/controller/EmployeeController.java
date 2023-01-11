package com.server.datn.server.controller;


import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.employee.EmployeeRequest;
import com.server.datn.server.helper.EmployeeHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeHelper helper;

    public EmployeeController(EmployeeHelper helper) {
        this.helper = helper;
    }


    @PostMapping("/fixed-employee")
    public ResponseEntity<?> createEmployee(@ModelAttribute EmployeeRequest employeeRequest) {
        Response response = helper.createEmployee(employeeRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/fixed-employee/{id}")
    public ResponseEntity<?> updateEmployee(@ModelAttribute EmployeeRequest employeeRequest, @PathVariable String id) {
        Response response = helper.updateEmployee(employeeRequest, id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/fixed-employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        Response response = helper.deleteEmployee(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }


    @GetMapping("/authen-employee")
    public ResponseEntity<?> getEmployeeFromJwt(Authentication authentication) {
        Response response = helper.authorizeEmployee(authentication);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-employee")
    public ResponseEntity<?> findEmployee(@RequestParam Map<String, String> map,
                                          @RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "20") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Response response = helper.findEmployee(map, pageable);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-employee/level/{level}")
    public ResponseEntity<?> findEmployeeLevel(@PathVariable String level) {
        Response response = helper.findEmployeeLevel(level);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-employee/{id}")
    public ResponseEntity<?> findEmployeeById(@PathVariable String id) {
        Response response = helper.findEmployeeById(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }





}
