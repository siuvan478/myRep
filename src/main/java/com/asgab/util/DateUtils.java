package com.asgab.util;


import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final String yyyyMMdd = "yyyy-MM-dd";
    private static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat sdf_ymd = new SimpleDateFormat(yyyyMMdd);
    private static final SimpleDateFormat sdf_ymdhms = new SimpleDateFormat(yyyyMMddHHmmss);

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

    /**
     * 获取目标日期的当前天上午九点
     *
     * @param targetDate 2017-08-07 09:00:00
     * @return 2017-08-08 09:00:00
     */
    public static String getCurrentDayAM900(Date targetDate) {
        try {
            String ymd = sdf_ymd.format(targetDate);
            ymd = ymd.concat(" ").concat("09:00:00");
            Date currDay = sdf_ymdhms.parse(ymd);
            if (isBeforeNow(currDay)) {
                return "";
            }
            return sdf_ymdhms.format(currDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取目标日期的前一天下午九点
     *
     * @param targetDate 2017-08-07 09:00:00
     * @return 2017-08-06 21:00:00
     */
    public static String getBeforeDayPM1630(Date targetDate) {
        try {
            String ymd = sdf_ymd.format(targetDate);
            ymd = ymd.concat(" ").concat("16:30:00");
            DateTime targetDateTime = new DateTime(sdf_ymdhms.parse(ymd));
            DateTime resultDateTime = targetDateTime.plusDays(-1);
            if (resultDateTime.isBeforeNow()) {
                return "";
            }
            return sdf_ymdhms.format(resultDateTime.toDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.set(2017, 10, 9);
        System.out.println(getBeforeDayPM1630(c.getTime()));
        System.out.println(getCurrentDayAM900(c.getTime()));
    }

}