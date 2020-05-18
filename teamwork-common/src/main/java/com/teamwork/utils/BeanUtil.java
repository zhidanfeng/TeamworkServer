package com.teamwork.utils;

import org.springframework.beans.BeanUtils;

public class BeanUtil {

    public static <T> T copy(Object source, Class<T> c) {
        try {
            T t = c.newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
