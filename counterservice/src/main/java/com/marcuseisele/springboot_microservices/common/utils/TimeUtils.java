package com.server.datn.server.common.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

import static com.server.datn.server.common.constants.AppConstants.*;

public class TimeUtils {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(DEFAULT_FORMAT_TIME);
    private static final SimpleDateFormat DATE_FORMAT_KEY = new SimpleDateFormat(DEFAULT_FORMAT_DATE_KEY);
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DEFAULT_FORMAT_DATE_TIME);

    public static Timestamp getTimestampNow(){
        return new Timestamp(new Date().getTime());
    }
    public static String getStringDateNow(){
        return DATE_FORMAT.format(new Date());
    }

    public static String parseString(Date date){
        return date != null ? DATE_TIME_FORMAT.format(date) : "";
    }

    public static Timestamp parseTimestamp(String s) throws ParseException {
        long time = TimeUtils.parseDateTime(s).getTime();
        return new Timestamp(time);
    }

    public static Timestamp parseDateTimestamp(String s) throws ParseException {
        long time = TimeUtils.parseDate(s).getTime();
        return new Timestamp(time);
    }

    public static Date parseDateTime(String s) throws ParseException {
        return DATE_TIME_FORMAT.parse(s);
    }

    public static Date parseDate(String s) throws ParseException {
        return DATE_FORMAT.parse(s);
    }

    public static String parseStringKey(Date date){
        return date != null ? DATE_FORMAT_KEY.format(date) : "";
    }

    public static String parseStringTime(Timestamp timestamp){
        return timestamp != null ? TIME_FORMAT.format(timestamp) : "";
    }

    public static String parseStringDate(Timestamp timestamp){
        return timestamp != null ? DATE_FORMAT.format(timestamp) : "";
    }

    public static int getDayOfMonth(String dateStr) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtils.parseDate(dateStr));
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        YearMonth yearMonthObject = YearMonth.of(year, month + 1);
        return yearMonthObject.lengthOfMonth();
    }

    public static Calendar parseCalandar(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtils.parseDate(date));
        return calendar;
    }

    public static String calculateTime(Timestamp from, Timestamp to, boolean late, boolean early){
        String[] fromStr = TimeUtils.parseStringTime(from).split(":");
        String[] toStr = TimeUtils.parseStringTime(to).split(":");

        int fromH = Integer.parseInt(fromStr[0]);
        int fromM = Integer.parseInt(fromStr[1]);
        long fromS = (fromH * 60 * 60L) + (fromM * 60L) ;

        int toH = Integer.parseInt(toStr[0]);
        int toM = Integer.parseInt(toStr[1]);
        long toS = (toH * 60 * 60L) + (toM * 60L) ;

        long total = toS - fromS + 1;
        long rsH = Math.max(total / 3600, 0);
        long rsM = Math.max((total % 3600) / 60, 0);
        long rsS = Math.max((total % 60), 0);
        return String.format("%02d:%02d:%02d", rsH, rsM, rsS);

    }

    public static boolean checkSameMonth(String fromDate, String toDate, String format){
        boolean check = false;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Calendar from = Calendar.getInstance();
            from.setTime(simpleDateFormat.parse(fromDate));
            int fromDay = from.get(Calendar.DAY_OF_MONTH);
            int fromMonth = from.get(Calendar.MONTH);
            int fromYear = from.get(Calendar.YEAR);

            Calendar to = Calendar.getInstance();
            to.setTime(simpleDateFormat.parse(toDate));
            int toDay = to.get(Calendar.DAY_OF_MONTH);
            int toMonth = to.get(Calendar.MONTH);
            int toYear = to.get(Calendar.YEAR);
            
            if(fromMonth == toMonth && fromYear == toYear){
                YearMonth yearMonthObject = YearMonth.of(fromYear, fromMonth + 1);
                int daysInMonth = yearMonthObject.lengthOfMonth();
                if(daysInMonth >= (toDay - fromDay + 1)){
                    check = true;
                }
            }
        }catch (Exception ignored){}

        return check;
    }
}
