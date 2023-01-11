package com.server.datn.server.services;

import java.util.Date;
import java.util.List;

public interface HolidaysService {
    boolean checkHolidays(Date date);
    List<String> getAllHoliday();
    void refreshData();
}
