package com.teamwork.vo;

import com.teamwork.annotation.RedisParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalPlanVO extends TaskVO {
    /**
     * 截止时间
     */
    private long endTime;
    /**
     * 重复类型
     */
    private int repeatType;
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
