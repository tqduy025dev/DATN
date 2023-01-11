package com.server.datn.server.services.impl;

import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.manager.Holidays;
import com.server.datn.server.repositories.HolidaysRepository;
import com.server.datn.server.services.HolidaysService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidaysServiceImpl implements HolidaysService, InitializingBean {
    private final HolidaysRepository holidaysRepository;
    private static HolidaysService instance;
    private static List<String> holidays;

    public HolidaysServiceImpl(HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }

    @Override
    public boolean checkHolidays(Date date) {
        boolean isHoliday;
        try {
            String key = TimeUtils.parseStringKey(date);
            isHoliday = holidaysRepository.existsById(key);
        }catch (Exception e){
            isHoliday = false;
        }
        return isHoliday;
    }

    @Override
    public List<String> getAllHoliday() {
        return holidays;
    }

    @Override
    public void refreshData() {
        holidays = holidaysRepository.findAll().stream().map(Holidays::getHolidayKey).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() {
        instance = this;
        holidays = holidaysRepository.findAll().stream().map(Holidays::getHolidayKey).collect(Collectors.toList());
    }

    public static HolidaysService getInstance(){
        return instance;
    }
}
