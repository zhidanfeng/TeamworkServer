package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务操作记录表
 */
@TableName(value = "t_task_attachment")
public class T_TASK_ATTACHMENT {
    /**
     * 记录编号
     */
    @TableId(value = "id", type = IdType.NONE)
    @Getter
    @Setter
    private String attachmentId;
    /**
     * 操作人员id
     */
    @TableField(value = "file_name")
    @Getter @Setter
    private String fileName;
    /**
     * 操作人员账号
     */
    @TableField(value = "file_size")
    @Getter @Setter
    private long fileSize;
    /**
     * 修改后的值
     */
    @TableField(value = "suffix_name")
    @Getter @Setter
    private String suffixName;
    /**
     * 所属任务id
     */
    @TableField(value = "task_id")
    @Getter @Setter
    private long taskId;
    /**
     * 关联的操作记录id
     */
    @TableField(value = "op_record_id")
    @Getter
    @Setter
    private Long opRecordId;
}
