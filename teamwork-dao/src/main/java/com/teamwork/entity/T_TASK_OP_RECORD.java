package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 任务操作记录表
 */
@TableName(value = "t_task_op_record")
public class T_TASK_OP_RECORD {
    /**
     * 记录编号
     */
    @TableId(value = "id", type = IdType.NONE)
    @Getter
    @Setter
    private Long recordId;

    /**
     * 记录类型
     */
    @TableField(value = "record_type")
    @Getter @Setter
    private int recordType;
    /**
     * 操作人员id
     */
    @TableField(value = "operator_id")
    @Getter @Setter
    private String operatorId;
    /**
     * 操作人员账号
     */
    @TableField(value = "operator_name")
    @Getter @Setter
    private String operatorName;
    /**
     * 修改后的值
     */
    @TableField(value = "new_value")
    @Getter @Setter
    private String newValue;
    /**
     * 关联附件id
     */
    @TableField(value = "attachment_id")
    @Getter @Setter
    private String attachmentId;
    /**
     * 所属任务id
     */
    @TableField(value = "task_id")
    @Getter @Setter
    private long taskId;

    @Getter
    @Setter
    private Date createTime;
}
