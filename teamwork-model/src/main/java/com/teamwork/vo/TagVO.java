package com.teamwork.vo;

import java.io.Serializable;
import java.util.Date;

public class TagVO implements Serializable {
    private String id;
    /**
     * 标签名称
     */
    private String tagName;
    private Date createTime;
    private Date updateTime;

    //region get and set

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    //endregion
}
