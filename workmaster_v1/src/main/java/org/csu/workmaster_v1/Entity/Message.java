package org.csu.workmaster_v1.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long groupid;
    public String messagename;
    public String messageabstract;
    public String messagecontent;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long time;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long messagepublisher;
    public List<Long> userreaded = new ArrayList<Long>();
    public String messagepublishername;

    public Message(long groupid, String messagename,String messageabstract, String messagecontent, long messagepublisher, String messagepublishername) {
        this.id = new Random().nextLong();
        this.groupid = groupid;
        this.messagename = messagename;
        this.messageabstract = messageabstract;
        this.messagecontent = messagecontent;
        this.time = System.currentTimeMillis();
        this.messagepublisher = messagepublisher;
        this.messagepublishername = messagepublishername;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getMessagepublishername() { return messagepublishername; }

    public void setMessagepublishername(String messagepublishername) { this.messagepublishername = messagepublishername; }

    public long getGroupid() {
        return groupid;
    }

    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

    public String getMessagename() {
        return messagename;
    }

    public void setMessagename(String messagename) {
        this.messagename = messagename;
    }

    public String getMessagecontent() {
        return messagecontent;
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getMessagepublisher() {
        return messagepublisher;
    }

    public void setMessagepublisher(long messagepublisher) {
        this.messagepublisher = messagepublisher;
    }

    public List<Long> getUserreaded() {
        return userreaded;
    }

    public void setUserreaded(List<Long> userreaded) {
        this.userreaded = userreaded;
    }

    public String getMessageabstract() {  return messageabstract;}

    public void setMessageabstract(String messageabstract) { this.messageabstract = messageabstract; }
}
