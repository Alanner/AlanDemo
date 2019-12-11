package org.csu.workmaster_v1.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long id;
    public String studentid;
    public String userpassword;
    public int verification_status=0;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public String avatar ;
    public String username;
    public List<Long> grouplist = new ArrayList<Long>();

    public User(){}

    public User(String studentid, String userpassword, String username) {
        this.studentid = studentid;
        this.userpassword = userpassword;
        this.username = username;
        id = new Random().nextInt(10000000);
        this.avatar = "http://pf8r5ullw.bkt.clouddn.com/-1257351316062730504";
    }

    public User(String username, String userpassword, long id) {
        this.username = username;
        this.userpassword = userpassword;
        this.id = id;
    }

    public User(long id, String studentId, String userpassword, String avatar, String username) {
        this.id = id;
        studentid = studentId;
        this.userpassword = userpassword;
        this.avatar = avatar;
        this.username = username;
    }

    public String getStudentid() {
        return studentid;
    }

    public List<Long> getGrouplist() {
        return grouplist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentid;
    }

    public void setStudentId(String studentId) {
        studentid = studentId;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public int getVerification_status() {
        return verification_status;
    }

    public void setVerification_status(int verification_status) {
        this.verification_status = verification_status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String toString(){
        return  ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
