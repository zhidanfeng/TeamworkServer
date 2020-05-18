package com.teamwork.service;

import com.teamwork.vo.Result;
import com.teamwork.vo.paging.PageRequestVO;

public interface BaseService<T> {
    Result selectAll();
    Result insert(T item);
    Result update(Long key, T item);
    Result deleteByKey(Long key);
    /**
     * 分页查询
     */
    default Result selectByPage(PageRequestVO pageRequestVO) {
        return Result.failure();
    }
}
