package org.csu.workmaster.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Random;

public class File {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long id;
    public String filehexid;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long uploader;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public long taskid;

    public File(String filehexid, long uploader, long taskid) {
        this.id = new Random().nextLong();
        this.filehexid = filehexid;
        this.uploader = uploader;
        this.taskid = taskid;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getUploader() { return uploader; }

    public void setUploader(long uploader) {
        this.uploader = uploader;
    }

    public long getTaskid() {
        return taskid;
    }

    public void setTaskid(long taskid) {
        this.taskid = taskid;
    }

    public String getFilehexid() { return filehexid; }

    public void setFilehexid(String filehexid) { this.filehexid = filehexid; }

}
