package com.teamwork.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    /**
     * 获取当前时间
     */
    public static Date getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        try {
            return format.parse(format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化日期
     */
    public static String formatDate(Date date, String pattern) {
        if(date == null)
            return "";

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String format(long time, String pattern) {
        Date date = new Date(time);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static Date convertTimestampToDate(long timestamp) {
        return timestamp == 0 ? null : new Date(timestamp);
    }

    public static Date convertTimestampToDate(String timestamp) {
        return new Date((long) Double.parseDouble(timestamp));
    }

    public static long convertDateToTimestamp(Date date) {
        if(date == null)
            return 0;
        return date.getTime();
    }
}

