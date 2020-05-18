package com.teamwork.vo.paging;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponseVO {
    /**
     * 当前页数
     */
    private int currentPage;
    /**
     * 每页想获取的行数
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private long totalSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 记录明细
     */
    private List<?> records;
}
