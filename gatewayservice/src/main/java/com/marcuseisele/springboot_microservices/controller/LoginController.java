package com.server.datn.server.controller;

import com.server.datn.server.common.dto.authorize.LoginRequest;
import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.helper.LoginHelper;
import com.server.datn.server.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final LoginHelper helper;

    @Autowired
    EmployeeService employeeService;
    @Autowired
    PasswordEncoder passwordEncoder;

    private LoginController(LoginHelper helper) {
        this.helper = helper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        Employee employee = new Employee();
//        employee.setEmail("admin@gmail.com");
//        employee.setGender("M");
//        employee.setPhone("0987654321");
//        employee.setDayOfBirth(new Date());
//        employee.setFirstName("Tran");
//        employee.setLastName("Duy");
//        employee.setMiddleName("Quang");
//        employee.setStatus("ACTV");
//        employee.setCreateBy("-1");
//
//        String password = KeyGenaratorUtils.getDefaultPassword(passwordEncoder);
//
//        Account account = new Account();
//        account.setUsername(employee.getEmail());
//        account.setStatus("ACTV");
//        account.setPassword(password);
//        account.setCreateBy("-1");
//
//
//        employee.setAccount(account);
//        employeeService.createEmployee(employee);



        Response response = helper.authorize(loginRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }


}
