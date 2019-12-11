package org.csu.workmaster.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Group {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long id;
    public String groupname;
    public String groupgescription;
    public List<Long> userlist =new ArrayList<Long>();
    public String groupavatar;

    public Group(long id, String groupname, String groupgescription, String groupavatar) {
        this.id = id;
        this.groupname = groupname;
        this.groupgescription = groupgescription;
        this.groupavatar = groupavatar;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupgescription() {
        return groupgescription;
    }

    public void setGroupgescription(String groupgescription) {
        this.groupgescription = groupgescription;
    }

    public List<Long> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<Long> userlist) {
        this.userlist = userlist;
    }

    public String getGroupavatar() {
        return groupavatar;
    }

    public void setGroupavatar(String groupavatar) {
        this.groupavatar = groupavatar;
    }
}
