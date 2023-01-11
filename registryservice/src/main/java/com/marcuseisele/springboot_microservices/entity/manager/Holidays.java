package com.server.datn.server.entity.manager;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Holidays {
    @Id
    private String holidayKey;
    private String holidayType;
    private String holidayName;

    public String getHolidayKey() {
        return holidayKey;
    }

    public void setHolidayKey(String holidayKey) {
        this.holidayKey = holidayKey;
    }

    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }
}
