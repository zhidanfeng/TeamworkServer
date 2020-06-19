package com.teamwork.vo;

import com.teamwork.annotation.RedisParam;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 需求信息
 */
@Getter
@Setter
public class RequirementVO extends TaskVO {
    /**
     * 需求来源
     */
    private int taskSource;
    /**
     * 需求提出人
     */
    private String proposer;
    /**
     * 需求优先级
     */
    private int priority;
    /**
     * 评估意见
     */
    private String opinion;
    /**
     * 需求计划上线时间
     */
    private long planOnlineTime;
    /**
     * 需求执行人
     */
    private String executor;
    /**
     * 备注
     */
    private String remark;
    /**
     * 业务id，用于在特殊情况下，判断多次请求是否是相同业务请求了多次
     */
    @RedisParam(name = "businessId")
    private String businessId;
}
