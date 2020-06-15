package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessVO extends BaseVO {
    /**
     * 业务id，用于在特殊情况下，判断多次请求是否是相同业务请求了多次
     */
    private String businessId;
}
