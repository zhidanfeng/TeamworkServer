package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TaskOpRecordVO extends BaseVO {
    @Getter
    @Setter
    private Long recordId;
    @Getter
    @Setter
    private int recordType;
    @Getter
    @Setter
    private String operatorName;
    @Getter
    @Setter
    private String newValue;
    @Getter
    @Setter
    private String attachmentId;
    @Getter
    @Setter
    private long taskId;

    @Getter
    @Setter
    private List<TaskAttachmentVO> attachmentList;

    @Getter
    @Setter
    private String viewId;
}
