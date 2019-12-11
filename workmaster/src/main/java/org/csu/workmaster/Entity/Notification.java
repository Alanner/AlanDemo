package org.csu.workmaster.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Random;

public class Notification {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public String senderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public String receiver;
    public String notifictioncontent;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long time;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long groupid;
    public int  notificationstatus;//状态 0  1  2

    public Notification(String senderId, String receiver, String notifictioncontent, long groupid) {
        this.id = new Random().nextLong();
        this.senderId = senderId;
        this.receiver = receiver;
        this.notifictioncontent = notifictioncontent;
        this.time = System.currentTimeMillis();
        this.groupid = groupid;
        this.notificationstatus = 0;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNotifictioncontent() {
        return notifictioncontent;
    }

    public void setNotifictioncontent(String notifictioncontent) {
        this.notifictioncontent = notifictioncontent;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getGroupid() {
        return groupid;
    }

    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

    public int getNotificationstatus() {
        return notificationstatus;
    }

    public void setNotificationstatus(int notificationstatus) {
        this.notificationstatus = notificationstatus;
    }

}
