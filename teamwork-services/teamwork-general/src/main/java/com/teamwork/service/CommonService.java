package com.teamwork.service;

public interface CommonService {
    int i = 0;
    /**
     * 生成页面id
     */
    String getViewId(String viewId);
    /**
     * 删除页面id
     */
    void deleteViewId(String viewId);
}
