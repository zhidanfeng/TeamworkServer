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
    private Long maxRowId;
    /**
     * 当前页记录最小id
     */
    private Long minRowId;
    /**
     * 相对于当前页的偏移页码，比如当前页是第5页，那么下一页是1，上一页是-1，下二页是2，上二页是-2
     */
    private int offsetPage;

    public PageRequestVO(int pageNum, int pageSize) {
        this.currentPage = pageNum;
        this.pageSize = pageSize;
    }
}
