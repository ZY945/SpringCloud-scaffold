package com.scaffold.commons.utils.util;

import com.scaffold.commons.utils.content.TimeContent;
import com.scaffold.commons.utils.content.TimeEnum;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    private static final SimpleDateFormat fullDateTimeFormat = new SimpleDateFormat(TimeContent.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    private static final SimpleDateFormat dateOnlyFormat = new SimpleDateFormat(TimeContent.YEAR_MONTH_DAY);
    private static final SimpleDateFormat timeOnlyFormat = new SimpleDateFormat(TimeContent.HOUR_MINUTE_SECOND);
    private static final SimpleDateFormat dateTimeWithoutSecondsFormat = new SimpleDateFormat(TimeContent.YEAR_MONTH_DAY_HOUR_MINUTE);


    public static String getFormatDateStr(long timestamp) {

        // Convert the timestamp to a Date object
//        Date date = new Date(timestamp * 1000); // Multiply by 1000 because Date uses milliseconds
        Date date = new Date(timestamp); // Multiply by 1000 because Date uses milliseconds

        // Format the Date object into a string using SimpleDateFormat
        return fullDateTimeFormat.format(date);
    }

    public static String getFormatDateStr(Date date) {

        // Format the Date object into a string using SimpleDateFormat
        return fullDateTimeFormat.format(date);
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static String getCurrentDateStr() {
        return fullDateTimeFormat.format(getCurrentDate());
    }

    public static String getAfterDateStr(long timestamp, TimeEnum timeEnum, int amount) {
        if (timeEnum == TimeEnum.HOUR) {
            return getFormatDateStr(timestamp + amount * 1000L * 60 * 60);
        } else if (timeEnum == TimeEnum.MINUTE) {
            return getFormatDateStr(timestamp + amount * 1000L * 60);
        } else if (timeEnum == TimeEnum.SECOND) {
            return getFormatDateStr(timestamp + amount * 1000L);
        }
        return null;
    }

    public static String getAfterCurrentDateStr(TimeEnum timeEnum, int amount) {
        Calendar nowTime = Calendar.getInstance();
        if (timeEnum == TimeEnum.HOUR) {
            return getAfterCurrentHourDateStr(nowTime, amount);
        } else if (timeEnum == TimeEnum.MINUTE) {
            return getAfterCurrentMinuteDateStr(nowTime, amount);
        } else if (timeEnum == TimeEnum.SECOND) {
            return getAfterCurrentSecondDateStr(nowTime, amount);
        }
        return null;
    }

    public static String getBeforeCurrentDateStr(TimeEnum timeEnum, int amount) {
        Calendar nowTime = Calendar.getInstance();
        if (timeEnum == TimeEnum.HOUR) {
            return getBeforeCurrentHourDateStr(nowTime, -amount);
        } else if (timeEnum == TimeEnum.MINUTE) {
            return getBeforeCurrentMinuteDateStr(nowTime, -amount);
        } else if (timeEnum == TimeEnum.SECOND) {
            return getBeforeCurrentSecondDateStr(nowTime, -amount);
        }
        return null;
    }

    public static String getAfterCurrentHourDateStr(Calendar time, int amount) {
        time.add(Calendar.HOUR, amount);
        return fullDateTimeFormat.format(time.getTime());
    }

    public static String getAfterCurrentMinuteDateStr(Calendar time, int amount) {
        time.add(Calendar.MINUTE, amount);
        return fullDateTimeFormat.format(time.getTime());
    }

    public static String getAfterCurrentSecondDateStr(Calendar time, int amount) {
        time.add(Calendar.SECOND, amount);
        return fullDateTimeFormat.format(time.getTime());
    }

    public static String getBeforeCurrentHourDateStr(Calendar time, int amount) {
        time.add(Calendar.HOUR, amount);
        return fullDateTimeFormat.format(time.getTime());
    }

    public static String getBeforeCurrentMinuteDateStr(Calendar time, int amount) {
        time.add(Calendar.MINUTE, amount);
        return fullDateTimeFormat.format(time.getTime());
    }

    public static String getBeforeCurrentSecondDateStr(Calendar time, int amount) {
        time.add(Calendar.SECOND, amount);
        return fullDateTimeFormat.format(time.getTime());
    }

    public static void main(String[] args) {

        System.out.println("getCurrentDateStr()===>" + getCurrentDateStr());
        System.out.println("getCurrentDate()===>" + getCurrentDate());
        System.out.println("getFormatDateStr()===>" + getFormatDateStr(System.currentTimeMillis() + 10000L));
        System.out.println("getAfterDateStr()===>" + getAfterDateStr(System.currentTimeMillis(), TimeEnum.HOUR, 1));
        System.out.println("getAfterDateStr()===>" + getAfterDateStr(System.currentTimeMillis(), TimeEnum.MINUTE, 1));
        System.out.println("getAfterDateStr()===>" + getAfterDateStr(System.currentTimeMillis(), TimeEnum.SECOND, 1));
        System.out.println("getFormatDateStr()===>" + getFormatDateStr(new Date()));
        System.out.println("getAfterCurrentDateStr(TimeEnum.HOUR,1)===>" + getAfterCurrentDateStr(TimeEnum.HOUR, 1));
        System.out.println("getBeforeCurrentDateStr(TimeEnum.HOUR,1)===>" + getBeforeCurrentDateStr(TimeEnum.HOUR, 1));

    }
}
