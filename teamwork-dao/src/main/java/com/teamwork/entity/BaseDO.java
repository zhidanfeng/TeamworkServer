package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseDO {
    /**
     * 创建时间
     */
    @TableField(value = "gmt_created")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified")
    private Date updateTime;
}
