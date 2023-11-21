package com.gaspay.ussdapp.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtils {
    private static final DateFormat REDISDATEFORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final DateFormat DATETIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public String formatRedisDate(Date dateObj){
        return REDISDATEFORMAT.format(dateObj);
    }
    public static String formatRedisDateObj(Date dateObj){
        return formatDateTime(dateObj);
    }


    public static Date parseDateTime(String dateString) throws ParseException {
        return DATETIMEFORMAT.parse(dateString);
    }

    public static String formatDateTime(Date dateObj){
        return DATETIMEFORMAT.format(dateObj);
    }
}
