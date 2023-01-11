package com.server.datn.server.services.impl;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.employee.EmployeeRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.KeyGenarator;
import com.server.datn.server.common.utils.ResourceBundle;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.account.Account;
import com.server.datn.server.entity.company.Office;
import com.server.datn.server.entity.files.SystemFile;
import com.server.datn.server.entity.job.JobLevel;
import com.server.datn.server.entity.job.JobTitle;
import com.server.datn.server.entity.user.Employee;
import com.server.datn.server.entity.user.Role;
import com.server.datn.server.repositories.EmployeRepository;
import com.server.datn.server.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

import static com.server.datn.server.common.constants.AppConstants.*;
import static com.server.datn.server.common.constants.MessageConstants.CREATE_ACCOUNT_MESS;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeRepository employeRepository;
    private final CloudService cloudService;
    private final OfficeService officeService;
    private final JobTitleService jobTitleService;
    private final JobLevelService jobLevelService;
    private final RoleService roleService;
    private final EntityManager entityManager;
    private final MailService mailService;


    @Value("${email.admin}")
    private String adminEmail;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeRepository employeRepository, CloudService cloudService, OfficeService officeService, JobTitleService jobTitleService, JobLevelService jobLevelService, RoleService roleService, EntityManager entityManager, MailService mailService) {
        this.employeRepository = employeRepository;
        this.cloudService = cloudService;
        this.officeService = officeService;
        this.jobTitleService = jobTitleService;
        this.jobLevelService = jobLevelService;
        this.roleService = roleService;
        this.entityManager = entityManager;
        this.mailService = mailService;
    }

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee getEmployeeRef(String id) {
        return employeRepository.getReferenceById(id);
    }

    @Override
    public Employee createEmployee(EmployeeRequest employeeRequest) throws IOException, ParseException {
        Employee employee = (Employee) AppUtils.converToEntities(employeeRequest, Employee.class);
        Role role = roleService.findRoleById(employeeRequest.getRole());
        String reportTo = employeeRequest.getReportTo();
        Employee assignee = null;
        if (StringUtils.hasLength(reportTo)) {
            assignee = employeRepository.findById(reportTo).orElse(null);
        }
        this.setDataEmployee(employeeRequest, employee);
        String[] password = KeyGenarator.getDefaultPassword(passwordEncoder);
        String url = cloudService.uploadToCloud(employeeRequest.getImage());
        SystemFile image = new SystemFile();
        image.setData(url);
        image.setType(employeeRequest.getImage().getContentType());
        image.setCreateBy(AppUtils.getCurrentUserId());

        Account account = new Account();
        account.setPassword(password[0]);
        account.setUsername(employeeRequest.getEmail());
        account.setCreateBy(AppUtils.getCurrentUserId());

        employee.setImage(image);
        employee.setAccount(account);
        employee.setCreateBy(AppUtils.getCurrentUserId());
        employee.setRole(role);
        employee.setReportTo(assignee);
        Employee result = employee;// employeRepository.save(employee);
        String content = ResourceBundle.getInstance().getName(CREATE_ACCOUNT_MESS);
        content = content.replaceAll("&mail", result.getEmail());
        content = content.replaceAll("&pass", password[1]);
        mailService.sendEmail(result.getEmail(), content,"EMPLOYEE ACCOUNT INFORMATION");
        return result;
    }

    @Override
    public BaseResult updateEmployee(EmployeeRequest employeeRequest, String id) throws ParseException, IOException {
        BaseResult result = new BaseResult();
        String status = STATUS_U2;
        boolean exists = employeRepository.existsById(id);
        if (exists) {
            Employee employeeOld = employeRepository.getReferenceById(id);
            final String email = employeeOld.getEmail();

            String oldPass = employeeRequest.getOldPassword();
            String newPass = employeeRequest.getNewPassword();
            String confirmPass = employeeRequest.getConfirmPassword();

            boolean changePassword = StringUtils.hasLength(oldPass) &&
                    StringUtils.hasLength(newPass) && StringUtils.hasLength(confirmPass);
            if (changePassword) {
                if (!newPass.equals(confirmPass)) {
                    result.setStatus(PASSWORD_UNVALID);
                    return result;
                }
                boolean match = passwordEncoder.matches(oldPass, employeeOld.getAccount().getPassword());
                if (!match) {
                    result.setStatus(PASSWORD_NOT_MATCH);
                    return result;
                }
                Account account = employeeOld.getAccount();
                account.setPassword(passwordEncoder.encode(confirmPass));
                account.setUpdateBy(AppUtils.getCurrentUserId());
                employeeOld.setAccount(account);
            }
            AppUtils.copyNonNullProperties(employeeRequest, employeeOld);
            this.setDataEmployee(employeeRequest, employeeOld);
            if (Objects.nonNull(employeeRequest.getRole())) {
                Role role = roleService.findRoleById(employeeRequest.getRole());
                employeeOld.setRole(role);
            }
            if (Objects.nonNull(employeeRequest.getReportTo())) {
                Employee reportTo = employeRepository.findById(employeeRequest.getReportTo()).orElse(null);
                employeeOld.setReportTo(reportTo);
            }
            if (Objects.nonNull(employeeRequest.getImage())) {
                String url = cloudService.uploadToCloud(employeeRequest.getImage());
                SystemFile image = new SystemFile();
                image.setData(url);
                image.setType(employeeRequest.getImage().getContentType());
                image.setCreateBy(AppUtils.getCurrentUserId());
                employeeOld.setImage(image);
            }
            employeeOld.setEmail(email);
            employeeOld.setLastUpdated(TimeUtils.getTimestampNow());
            employeeOld.setUpdateBy(AppUtils.getCurrentUserId());
            Employee employee = employeRepository.save(employeeOld);
            result.setResult(employee);
            status = STATUS_U0;
        }
        result.setStatus(status);
        return result;
    }

    private void setDataEmployee(EmployeeRequest employeeRequest, Employee employeeOld) throws ParseException {
        if (Objects.nonNull(employeeRequest.getOfficeId())) {
            Office office = officeService.findOfficeById(employeeRequest.getOfficeId());
            employeeOld.setOffice(office);
        }
        if (Objects.nonNull(employeeRequest.getJobLevelId())) {
            JobLevel jobLevel = jobLevelService.findJobLevelById(employeeRequest.getJobLevelId());
            employeeOld.setJobLevel(jobLevel);
        }
        if (Objects.nonNull(employeeRequest.getJobTitleId())) {
            JobTitle jobTitle = jobTitleService.findJobTitleById(employeeRequest.getJobTitleId());
            employeeOld.setJobTitle(jobTitle);
        }
        if (Objects.nonNull(employeeRequest.getDayOfBirth())) {
            Timestamp timestamp = TimeUtils.parseDateTimestamp(employeeRequest.getDayOfBirth());
            employeeOld.setDayOfBirth(timestamp);
        }
    }

    @Override
    public Employee findEmployeeByUserName(String username) {
        return employeRepository.findEmployeeByAccountUsername(username);
    }

    @Override
    public Employee findEmployeeById(String id) {
        return employeRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existEmployeeById(String id) {
        return employeRepository.existsById(id);
    }

    @Override
    public String deleteEmployeeById(String id) {
        String status = STATUS_D2;
        boolean exists = employeRepository.existsById(id);
        if (exists) {
            Employee employee = employeRepository.getReferenceById(id);
            if (STATUS_DELETE.equals(employee.getStatus())) {
                status = STATUS_D1;
            } else {
                employee.setStatus(STATUS_DELETE);
                employeRepository.save(employee);
                status = STATUS_D0;
            }
        }
        return status;
    }


    @Override
    public Page<Employee> findEmployeeByFields(Map<String, String> map, Pageable pageable) throws ParseException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        Predicate[] arrPredicate = addQueryCondition(criteriaBuilder, root, map);
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(root.get(FIELD_EMPLOYEE_LASTNAME)));
        orders.add(criteriaBuilder.asc(root.get(FIELD_EMPLOYEE_MIDDLENAME)));
        orders.add(criteriaBuilder.asc(root.get(FIELD_EMPLOYEE_FIRSTNAME)));
        criteriaQuery.select(root).where(arrPredicate).orderBy(orders);

        List<Employee> result = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Employee.class))).where(arrPredicate);
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public Page<Employee> findAllEmployee(Pageable pageable) {
        return employeRepository.findAllByEmailNot(adminEmail,pageable);
    }

    @Override
    public List<Employee> findEmployeeByLevel(int level) {
        return employeRepository.findEmployeeByJobLevelLevelIsGreaterThan(level);
    }

    @Override
    public List<Employee> findEmployeeByLevel(String id) {
        return employeRepository.findEmployeeByReportToEmployeeId(id);
    }

    private Predicate[] addQueryCondition(CriteriaBuilder criteriaBuilder, Root<Employee> root, Map<String, String> map) throws ParseException {
        map.values().removeAll(Collections.singleton(null));
        map.values().removeAll(Collections.singleton(""));
        List<Predicate> predicates = new ArrayList<>();
        for (String key : map.keySet()) {
            String keysearch = AppUtils.removeAccent(map.get(key));
            switch (key) {
                case FIELD_EMPLOYEE_ID:
                    predicates.add(criteriaBuilder.equal(root.get(FIELD_EMPLOYEE_ID), map.get(key)));
                    break;
                case FIELD_EMPLOYEE_EMAIL:
                    predicates.add(criteriaBuilder.like(root.get(FIELD_EMPLOYEE_EMAIL), "%" + keysearch + "%"));
                    break;
                case FIELD_EMPLOYEE_LASTNAME:
                    predicates.add(criteriaBuilder.like(root.get(FIELD_EMPLOYEE_LASTNAME), "%" + keysearch + "%"));
                    break;
                case FIELD_EMPLOYEE_ADDRESS:
                    predicates.add(criteriaBuilder.like(root.get(FIELD_EMPLOYEE_ADDRESS), "%" + keysearch + "%"));
                    break;
                case FIELD_EMPLOYEE_PHONE:
                    predicates.add(criteriaBuilder.like(root.get(FIELD_EMPLOYEE_PHONE), "%" + keysearch + "%"));
                    break;
                case FIELD_EMPLOYEE_GENDER:
                    predicates.add(criteriaBuilder.like(root.get(FIELD_EMPLOYEE_GENDER), map.get(key)));
                    break;
                case FIELD_EMPLOYEE_DAYOFBIRTH:
                    Timestamp timestamp = TimeUtils.parseTimestamp(map.get(key) + " 00:00:00");
                    predicates.add(criteriaBuilder.equal(root.get(FIELD_EMPLOYEE_DAYOFBIRTH), timestamp));
                    break;
                case FIELD_EMPLOYEE_STATUS:
                    predicates.add(criteriaBuilder.equal(root.get(FIELD_EMPLOYEE_STATUS), map.get(key)));
                    break;
            }
        }
        predicates.add(criteriaBuilder.notEqual(root.get(FIELD_EMPLOYEE_EMAIL), adminEmail));
        Predicate[] predicatesArr = new Predicate[predicates.size()];
        return predicates.toArray(predicatesArr);
    }

}
