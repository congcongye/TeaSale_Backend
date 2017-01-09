package com.cxtx.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by jinchuyang on 17/1/8.
 */
public class DateUtils {
    public static Date parse(String dateStr){
        int year = Integer.parseInt(dateStr.substring(3, 7));
        int month = Integer.parseInt(dateStr.substring(0,2))-1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,1);
        Date date = calendar.getTime();
        return date;
    }

    public static Date nextMonth(Date date) {
        Date newDate = new Date();
        int month = date.getMonth();
        if (month == 11){
            newDate.setMonth(0);
            newDate.setYear(date.getYear()+1);
            newDate.setDate(1);
        }else {
            newDate.setMonth(date.getMonth()+1);
            newDate.setDate(1);
            newDate.setYear(date.getYear());
        }
        return newDate;
    }
}
