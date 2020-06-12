package com.teamwork.service;

import com.teamwork.vo.Result;
import com.teamwork.vo.paging.PageRequestVO;

import java.util.List;

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

    /**
     * 批量插入
     */
    default Result insertBatch(List<T> items) {
        return Result.failure();
    }
}
