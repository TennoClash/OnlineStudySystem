package oss.entity;

import java.util.Date;

public class Courseware {
    private int coursewareId;

    private int courseId;

    private String coursewareName;

    private String videoPath;

    private String picPath;

    private String videoTime;

    private Date createTime;

    private Date modifyTime;

    private String coursewareText;

    public int getCoursewareId() {
        return coursewareId;
    }

    public void setCoursewareId(int coursewareId) {
        this.coursewareId = coursewareId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCoursewareName() {
        return coursewareName;
    }

    public void setCoursewareName(String coursewareName) {
        this.coursewareName = coursewareName == null ? null : coursewareName.trim();
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath == null ? null : videoPath.trim();
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime == null ? null : videoTime.trim();
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

    public String getCoursewareText() {
        return coursewareText;
    }

    public void setCoursewareText(String coursewareText) {
        this.coursewareText = coursewareText == null ? null : coursewareText.trim();
    }
}