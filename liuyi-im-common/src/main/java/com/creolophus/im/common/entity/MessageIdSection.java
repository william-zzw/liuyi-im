package com.creolophus.im.common.entity;

import com.creolophus.liuyi.common.base.AbstractEntity;
import org.beetl.sql.core.annotatoin.AssignID;

import java.util.Date;


/**
 * @author magicnana
 * @date 2020-07-03
 */

public class MessageIdSection extends AbstractEntity {

    @AssignID
    private Long sectionId;
    private Long currentId;
    private Long maxId;
    private Long step;
    private Date createTime;
    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCurrentId() {
        return currentId;
    }

    public void setCurrentId(Long currentId) {
        this.currentId = currentId;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
