package com.teamwork.vo.paging;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PageRequestVO {
    /**
     * 当前页数
     */
    private int currentPage;
    /**
     * 每页想获取的行数
     */
    private int pageSize;
    /**
     * 当前页记录最大id
     */
    private int maxRowId;
    /**
     * 当前页记录最小id
     */
    private int minRowId;

    public PageRequestVO(int pageNum, int pageSize) {
        this.currentPage = pageNum;
        this.pageSize = pageSize;
    }
}
