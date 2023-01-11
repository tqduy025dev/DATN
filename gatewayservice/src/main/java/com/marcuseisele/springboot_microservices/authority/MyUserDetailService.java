package com.server.datn.server.authority;

import com.server.datn.server.entity.user.Employee;
import com.server.datn.server.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(MyUserDetailService.class);
    private final EmployeeService employeeService;

    @Autowired
    public MyUserDetailService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeService.findEmployeeByUserName(username);
        if(employee != null){
            return new MyUserDetail(employee);
        }else{
            log.debug("User not found with emailId: " + username);
            throw new UsernameNotFoundException("User not found with emailId: " + username);
        }
    }
}
