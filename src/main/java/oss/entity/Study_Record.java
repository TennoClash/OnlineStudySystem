package oss.entity;

import java.util.Date;

public class Study_Record {
    private int recordId;

    private int batchId;

    private int userId;

    private int coursewareId;

    private int studyTimes;

    private int isPassTest;

    private String viewTime;

    private Date createTime;

    private Date modifyTime;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCoursewareId() {
        return coursewareId;
    }

    public void setCoursewareId(int coursewareId) {
        this.coursewareId = coursewareId;
    }

    public int getStudyTimes() {
        return studyTimes;
    }

    public void setStudyTimes(int studyTimes) {
        this.studyTimes = studyTimes;
    }

    public int getIsPassTest() {
        return isPassTest;
    }

    public void setIsPassTest(int isPassTest) {
        this.isPassTest = isPassTest;
    }

    public String getViewTime() {
        return viewTime;
    }

    public void setViewTime(String viewTime) {
        this.viewTime = viewTime == null ? null : viewTime.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}