package com.scaffold.commons.utils.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static final SimpleDateFormat fullDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat dateTimeWithoutSecondsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public static String getCurrentTime(long timestamp) {

        // Convert the timestamp to a Date object
        Date date = new Date(timestamp * 1000); // Multiply by 1000 because Date uses milliseconds

        // Format the Date object into a string using SimpleDateFormat
        return fullDateTimeFormat.format(date);
    }
}
