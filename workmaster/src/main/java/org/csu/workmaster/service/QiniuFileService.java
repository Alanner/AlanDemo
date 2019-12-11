package org.csu.workmaster.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QiniuFileService {
    public String uploaduserImage(MultipartFile multipartfile, long uploader);
    public String uploadergroupImage(MultipartFile multipartfile, long groupid);
    public int uploaderTaskfile(MultipartFile multipartfile, long uploader, long taskid);

    public String getuserimage(long userid);
    public String getgroupimage(long groupid);
    public List<String> getTaskFile(long taskid);
}
