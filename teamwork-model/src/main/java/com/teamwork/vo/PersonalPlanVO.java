package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

public class PersonalPlanVO extends TaskVO {
    /**
     * 截止时间
     */
    @Getter
    @Setter
    private long endTime;
    /**
     * 重复类型
     */
    @Getter @Setter
    private int repeatType;
    /**
     * 备注
     */
    @Getter @Setter
    private String remark;
}
