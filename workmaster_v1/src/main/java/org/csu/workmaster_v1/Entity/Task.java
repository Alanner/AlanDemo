package org.csu.workmaster_v1.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long groupid;
    public String taskname;
    public String taskcontent;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long time;
    public String deadline;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long taskpublisher;
    public String taskpublishername;
    public List userdone = new ArrayList<Long>();
    public String fileformat;
    public List filelist = new ArrayList<Long>();
    public int taskstatus;
    public String tasktext;

    public Task(long groupid, String taskname, String taskcontent, long taskpublisher,String taskpublishername,String fileformat,String deadline,String tasktext) {
        this.id = new Random().nextLong();
        this.groupid = groupid;
        this.taskname = taskname;
        this.taskcontent = taskcontent;
        this.time = System.currentTimeMillis();
        this.taskpublisher = taskpublisher;
        this.taskpublishername = taskpublishername;
        this.fileformat = fileformat;
        this.taskstatus = 0;
        this.deadline = deadline;
        this.tasktext = tasktext;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupid() {
        return groupid;
    }

    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

    public String getTasktext() {
        return tasktext;
    }

    public void setTasktext(String tasktext) {
        this.tasktext = tasktext;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTaskcontent() {
        return taskcontent;
    }

    public void setTaskcontent(String taskcontent) {
        this.taskcontent = taskcontent;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTaskpublisher() {
        return taskpublisher;
    }

    public void setTaskpublisher(long taskpublisher) {
        this.taskpublisher = taskpublisher;
    }

    public List getUserdone() {
        return userdone;
    }

    public void setUserdone(List userdone) {
        this.userdone = userdone;
    }

    public String getFileformat() {
        return fileformat;
    }

    public void setFileformat(String fileformat) {this.fileformat = fileformat; }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public List getFilelist() { return filelist;}

    public void setFilelist(List filelist) { this.filelist = filelist;}

    public int getTaskstatus() { return taskstatus;}

    public void setTaskstatus(int taskstatus) { this.taskstatus = taskstatus; }

    public String getTaskpublishername() { return taskpublishername; }

    public void setTaskpublishername(String taskpublishername) { this.taskpublishername = taskpublishername; }
}

