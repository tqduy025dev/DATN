package com.server.datn.server.common.dto.manager;

import com.fasterxml.jackson.annotation.JsonInclude;

public class WorkingTimeRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fromDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String toDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String location;
    private String locationIn;
    private String locationOut;
    private String timeCheckIn;
    private String timeCheckOut;
    private String checkDate;
    private String employeeId;
    private Boolean isHoliday;
    private Boolean isDayOff;
    private Integer isTakeLeave;
    private String lateTime;
    private String earlyTime;
    private String totalTime;

    public String getLocationIn() {
        return locationIn;
    }

    public void setLocationIn(String locationIn) {
        this.locationIn = locationIn;
    }

    public String getLocationOut() {
        return locationOut;
    }

    public void setLocationOut(String locationOut) {
        this.locationOut = locationOut;
    }

    public String getTimeCheckIn() {
        return timeCheckIn;
    }

    public void setTimeCheckIn(String timeCheckIn) {
        this.timeCheckIn = timeCheckIn;
    }

    public String getTimeCheckOut() {
        return timeCheckOut;
    }

    public void setTimeCheckOut(String timeCheckOut) {
        this.timeCheckOut = timeCheckOut;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getHoliday() {
        return isHoliday;
    }

    public void setHoliday(Boolean holiday) {
        isHoliday = holiday;
    }

    public Boolean getDayOff() {
        return isDayOff;
    }

    public void setDayOff(Boolean isDayOff) {
        this.isDayOff = isDayOff;
    }

    public String getLateTime() {
        return lateTime;
    }

    public void setLateTime(String lateTime) {
        this.lateTime = lateTime;
    }

    public String getEarlyTime() {
        return earlyTime;
    }

    public void setEarlyTime(String earlyTime) {
        this.earlyTime = earlyTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }


    public Integer getTakeLeave() {
        return isTakeLeave;
    }

    public void setTakeLeave(Integer takeLeave) {
        isTakeLeave = takeLeave;
    }
}
