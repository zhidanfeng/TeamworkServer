package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 需求信息
 */
public class RequirementVO extends TaskVO {
    /**
     * 需求来源
     */
    @Getter
    @Setter
    private int taskSource;
    /**
     * 需求提出人
     */
    @Getter
    @Setter
    private String proposer;
    /**
     * 需求优先级
     */
    @Getter @Setter
    private int priority;
    /**
     * 评估意见
     */
    @Getter @Setter
    private String opinion;
    /**
     * 需求提出时间
     */
    @Getter @Setter
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private long startTime;
    /**
     * 需求要求完成时间
     */
    @Getter @Setter
    private long endTime;
    /**
     * 需求计划上线时间
     */
    @Getter @Setter
    private long planOnlineTime;
    /**
     * 需求执行人
     */
    @Getter @Setter
    private String executor;
    /**
     * 备注
     */
    @Getter
    @Setter
    private String remark;
}
