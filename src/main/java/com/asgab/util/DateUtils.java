package com.asgab.util;


import org.joda.time.DateTime;

import java.util.Date;

public class DateUtils {

    public static boolean isSunday(Date date) {
        return getDayOfWeek(date) == 7;
    }

    /**
     * 这一天是星期几，1：星期一，7：星期天
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        return new DateTime(date).getDayOfWeek();
    }

    public static boolean isBeforeNow(Date date) {
        return new DateTime(date).isBeforeNow();
    }

    public static boolean isAfterNow(Date date) {
        return new DateTime(date).isAfterNow();
    }

    public static Date plusMonths(Date date, int plusNum) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(plusNum).toDate();
    }

}