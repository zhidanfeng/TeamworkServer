package com.teamwork.utils;

public class ThreadLocalUtil {

    private static ThreadLocal<String> logIdTL = new ThreadLocal<>();

    public static void setLogId(String logId) {
        logIdTL.set(logId);
    }

    public static String getLogId() {
        return logIdTL.get();
    }
}
