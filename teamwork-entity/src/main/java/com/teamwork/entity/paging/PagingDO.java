package com.teamwork.entity.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingDO {
    /**
     * 偏移量
     */
    private int offset;
    /**
     * 想要获取每页的记录数
     */
    private int rows;
    /**
     * 当前页记录最大id
     */
    private Long maxRowId;
    /**
     * 当前页记录最小id
     */
    private Long minRowId;
}
