package com.teamwork.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamwork.exceptions.TeamworkException;
import org.springframework.util.StringUtils;

public class JsonUtil {
    public static String objectToJson(Object value) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(value);
    }

    public static <T> T jsonToObject(String json, Class<T> valueType) throws Exception {
        if(StringUtils.isEmpty(json)) {
            throw new TeamworkException("json is null");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, valueType);
    }
}
