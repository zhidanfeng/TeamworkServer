package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

public class TaskAttachmentVO {
    @Getter
    @Setter
    private String attachmentId;
    @Getter
    @Setter
    private String fileName;
    @Getter
    @Setter
    private long fileSize;
    @Getter
    @Setter
    private String suffixName;
    @Getter
    @Setter
    private long taskId;
    @Getter
    @Setter
    private long opRecordId;
}
