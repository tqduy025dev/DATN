package com.server.datn.server.common.utils;

import com.server.datn.server.authority.JwtTokenProvider;
import com.server.datn.server.common.constants.AppConstants;
import com.server.datn.server.common.converter.StringToTimestampConverter;
import com.server.datn.server.common.converter.TimestampToStringConverter;
import com.server.datn.server.common.dto.base.FileResponse;
import com.server.datn.server.common.dto.company.OfficeResponse;
import com.server.datn.server.common.dto.employee.EmployeeResponse;
import com.server.datn.server.common.dto.manager.WorkingTimeRequest;
import com.server.datn.server.common.dto.user.RoleResponse;
import com.server.datn.server.entity.company.Company;
import com.server.datn.server.entity.manager.WorkFlowTakeLeave;
import com.server.datn.server.entity.manager.WorkingTime;
import com.server.datn.server.entity.user.Employee;
import com.server.datn.server.services.impl.HolidaysServiceImpl;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.server.datn.server.common.constants.AppConstants.CHECK_OUT;

public class AppUtils {
    public static String getCurrentUserId() {
        return JwtTokenProvider.getInstance().getUserIdFromAuth();
    }

    public static Employee getCurrentUser() {
        return JwtTokenProvider.getInstance().getUserFromAuth();
    }

    public static List<String> getHoliday(){
        return HolidaysServiceImpl.getInstance().getAllHoliday();
    }

    public static WorkingTimeRequest getCheckInCheckOut(WorkingTime check) {
        if (Objects.nonNull(check)){
            WorkingTimeRequest checkInCheckOut = new WorkingTimeRequest();
            String time = TimeUtils.parseStringTime(check.getTime());
            String date = TimeUtils.parseStringDate(check.getTime());
            if(check.getType().equals(AppConstants.CHECK_IN)){
                checkInCheckOut.setTimeCheckIn(time);
                checkInCheckOut.setLocationIn(check.getLocation());
            }else {
                checkInCheckOut.setTimeCheckOut(time);
                checkInCheckOut.setLocationOut(check.getLocation());
            }
            checkInCheckOut.setCheckDate(date);
            checkInCheckOut.setEmployeeId(check.getEmployeeId());
            return checkInCheckOut;
        }
        return null;
    }

    public static int takeLeave(WorkingTimeRequest i, List<WorkFlowTakeLeave> takeLeaveEmployees){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            timestamp = TimeUtils.parseDateTimestamp(i.getCheckDate());
        } catch (ParseException ignore) {}
        Timestamp finalTimestamp = timestamp;
        WorkFlowTakeLeave workFlowTakeLeave = takeLeaveEmployees.stream().filter(s -> finalTimestamp.compareTo(s.getTakeLeaveFrom()) >= 0 &&
                finalTimestamp.compareTo(s.getTakeLeaveTo()) <= 0).findFirst().orElse(null);
        int leave = 0;
        if(Objects.nonNull(workFlowTakeLeave)){
            leave = workFlowTakeLeave.getCategory().getPayable() ? 1 : 2;
        }
        return leave;
    }

    public static EmployeeResponse getEmployeeResponse(Employee employee) {
        if (Objects.nonNull(employee)) {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            FileResponse fileResponse = new FileResponse();
            fileResponse.setType(employee.getImage().getType());
            fileResponse.setData(employee.getImage().getData());
            fileResponse.setFileId(employee.getImage().getFileId());

            employeeResponse.setStatus(employee.getStatus());
            employeeResponse.setAddress(employee.getAddress());
            employeeResponse.setPhone(employee.getPhone());
            employeeResponse.setFirstName(employee.getFirstName());
            employeeResponse.setMiddleName(employee.getMiddleName());
            employeeResponse.setLastName(employee.getLastName());
            employeeResponse.setEmail(employee.getEmail());
            employeeResponse.setDayOfbirth(TimeUtils.parseString(employee.getDayOfBirth()));
            employeeResponse.setId(employee.getEmployeeId());
            employeeResponse.setGender(employee.getGender());
            employeeResponse.setCreateTime(TimeUtils.parseString(employee.getCreatedTime()));
            employeeResponse.setLastUpdated(TimeUtils.parseString(employee.getCreatedTime()));
            employeeResponse.setImage(fileResponse);
            employeeResponse.setDaysOfLeave(employee.getDaysOfLeave());

            EmployeeResponse reportTo = (EmployeeResponse) AppUtils.converToDTO(employee.getReportTo(), EmployeeResponse.class);
            employeeResponse.setReportTo(reportTo);

            RoleResponse roleResponse = (RoleResponse) AppUtils.converToDTO(employee.getRole(), RoleResponse.class);
            employeeResponse.setRole(roleResponse);

            if (Objects.nonNull(employee.getOffice())) {
                OfficeResponse officeResponse = (OfficeResponse) converToDTO(employee.getOffice(), OfficeResponse.class);
                employeeResponse.setOffice(officeResponse);
            }

            employeeResponse.setJobLevel(employee.getJobLevel());
            employeeResponse.setJobTitle(employee.getJobTitle());

            return employeeResponse;
        }
        return null;
    }

    public static Object converToDTO(Object object, Class<?> clazz) {
        if (Objects.nonNull(object)) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.addConverter(new TimestampToStringConverter());
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT);
            return modelMapper.map(object, clazz);
        }
        return null;
    }

    public static Object converToEntities(Object object, Class<?> clazz) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addConverter(new StringToTimestampConverter());
        return modelMapper.map(object, clazz);
    }

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : propertyDescriptors) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").trim();
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    public static Timestamp getCurrentTimeBeginDay(){
        Calendar begin = Calendar.getInstance();
        begin.set(Calendar.HOUR_OF_DAY, 0);
        begin.set(Calendar.MINUTE, 0);
        begin.set(Calendar.SECOND, 0);
        return new Timestamp(begin.getTimeInMillis());
    }

    public static Timestamp getCurrentTimeEndDay(){
        Calendar begin = Calendar.getInstance();
        begin.set(Calendar.HOUR_OF_DAY, 23);
        begin.set(Calendar.MINUTE, 59);
        begin.set(Calendar.SECOND, 59);
        return new Timestamp(begin.getTimeInMillis());
    }

    public static String getKeyDate(int day, int month, int year){
        return String.format("%02d", month).concat("/").concat(String.format("%02d", day)).concat("/").concat(String.format("%02d", year));
    }


    public static List<WorkingTimeRequest> getWorkingTimeRequestFromData(Map<String, List<WorkingTime>> map, Company company) throws ParseException {
        List<WorkingTimeRequest> workingTimeRequests = new ArrayList<>();
        String companyStartTime = TimeUtils.getStringDateNow() + " " + company.getStartWorkTime() + ":00";
        String companyEndTime = TimeUtils.getStringDateNow() + " " + company.getEndWorkTime() + ":00";
        Timestamp startWorking = TimeUtils.parseTimestamp(companyStartTime);
        Timestamp endWorking = TimeUtils.parseTimestamp(companyEndTime);

        for (String key : map.keySet()) {
            WorkingTimeRequest workingTimeRequest = new WorkingTimeRequest();
            workingTimeRequest.setCheckDate(key);
            Calendar calendar = TimeUtils.parseCalandar(key);
            workingTimeRequest.setDayOff(!company.getWorkDayOfWeek().contains(calendar.get(Calendar.DAY_OF_WEEK)));
            String keyHoliday = String.valueOf(key);
            boolean isHoliday = AppUtils.getHoliday().contains(keyHoliday.replaceAll("/", ""));
            workingTimeRequest.setHoliday(isHoliday);

            List<WorkingTime> workingTimes = map.get(key);
            Timestamp start = TimeUtils.parseTimestamp(key + " 00:00:00");
            Timestamp end = TimeUtils.parseTimestamp(key + " 00:00:00");
            for (WorkingTime workingTime : workingTimes) {
                workingTimeRequest.setEmployeeId(workingTime.getEmployeeId());
                if (workingTime.getType().equals(CHECK_OUT)) {
                    workingTimeRequest.setTimeCheckOut(TimeUtils.parseStringTime(workingTime.getTime()));
                    workingTimeRequest.setLocationOut(workingTime.getLocation());
                    end = workingTime.getTime();
                } else {
                    workingTimeRequest.setTimeCheckIn(TimeUtils.parseStringTime(workingTime.getTime()));
                    workingTimeRequest.setLocationIn(workingTime.getLocation());
                    start = workingTime.getTime();
                }
            }

            String lateTime = TimeUtils.calculateTime(startWorking, start, true, false);
            String earlyTime = TimeUtils.calculateTime(end, endWorking, false, true);
            String totalTime = TimeUtils.calculateTime(start, end, false, false);

            workingTimeRequest.setTotalTime(totalTime);
            workingTimeRequest.setEarlyTime(earlyTime);
            workingTimeRequest.setLateTime(lateTime);

            workingTimeRequests.add(workingTimeRequest);
        }

        return workingTimeRequests;
    }

}
