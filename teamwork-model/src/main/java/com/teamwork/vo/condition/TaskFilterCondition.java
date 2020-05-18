package com.teamwork.vo.condition;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class TaskFilterCondition {
    @Getter
    @Setter
    private String projectId;
    @Getter
    @Setter
    private Long startTime;
    @Getter
    @Setter
    private Long endTime;
    @Getter
    @Setter
    private Boolean finished;
    @Getter
    @Setter
    private Date startTimeDt;
    @Getter
    @Setter
    private Date endTimeDt;
    @Getter
    @Setter
    private String taskName;
    @Getter
    @Setter
    private Long createTime;
    @Getter
    @Setter
    private Date createTimeDt;

    /**
     * 将Long类型时间戳转为日期类型，用于数据库查询
     *
     * 客户端与服务端间数据传输时，表示日期用的是时间戳传递的
     */
    public void longToDate() {
        if(this.getStartTime() != null) {
            this.setStartTimeDt(DateUtil.date(this.getStartTime()));
        }
        if(this.getEndTime() != null) {
            this.setEndTimeDt(DateUtil.date(this.getEndTime()));
        }
    }
}
