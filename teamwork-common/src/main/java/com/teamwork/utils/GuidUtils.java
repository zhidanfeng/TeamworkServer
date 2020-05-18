package com.teamwork.utils;

import java.util.UUID;

public class GuidUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
