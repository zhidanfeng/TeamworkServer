package com.teamwork.service;

public interface CommonService {
    int i = 0;
    /**
     * 生成页面id
     */
    String getViewId();
    /**
     * 判断页面id是否存在
     */
    boolean existViewId(String viewId);
    /**
     * 删除页面id
     */
    void deleteViewId(String viewId);
}
