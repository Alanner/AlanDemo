package org.csu.workmaster_v1.service.impl;

import org.apache.commons.io.FileUtils;
import org.csu.workmaster_v1.Dao.FileDao;
import org.csu.workmaster_v1.Dao.GroupDao;
import org.csu.workmaster_v1.Dao.TaskDao;
import org.csu.workmaster_v1.Dao.UserDao;
import org.csu.workmaster_v1.Entity.Group;
import org.csu.workmaster_v1.Entity.Task;
import org.csu.workmaster_v1.Entity.User;
import org.csu.workmaster_v1.Util.QiniuCloudUtil;
import org.csu.workmaster_v1.Util.Url;
import org.csu.workmaster_v1.service.QiniuFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class QiniuFileServiceImpl implements QiniuFileService {
    @Autowired
    private FileDao fileDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;

    @Override
    public String uploaduserImage(MultipartFile multipartfile, long uploader) {
        try{
            File file = new File(multipartfile.getOriginalFilename());
            if(!multipartfile.isEmpty()){
                FileUtils.copyInputStreamToFile(multipartfile.getInputStream(), file);
                String Filename = String.valueOf(uploader)+System.currentTimeMillis();
                QiniuCloudUtil.put64image((File)file,Filename);
                //对User情况进行改变
                User user = userDao.findUserByUserId(uploader);
                user.setAvatar(Url.url+"/"+Filename);
                userDao.saveUser(user);
                //如果不需要File文件可删除
                if(file.exists()){
                    file.delete();
                }
                return Url.url+"/"+Filename;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String uploadergroupImage(MultipartFile multipartfile, long groupid) {
        try{
            File file = new File(multipartfile.getOriginalFilename());
            if(!multipartfile.isEmpty()){
                FileUtils.copyInputStreamToFile(multipartfile.getInputStream(), file);
                String Filename = String.valueOf(groupid);
                QiniuCloudUtil.put64image((File)file,Filename);
                //如果不需要File文件可删除
                if(file.exists()){
                    file.delete();
                }
                return Url.url+"/"+Filename;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int uploaderTaskfile(MultipartFile multipartfile, long uploader, long taskid) {
        try{
            File file = new File(multipartfile.getOriginalFilename());
            if(!multipartfile.isEmpty()){
                FileUtils.copyInputStreamToFile(multipartfile.getInputStream(), file);
                String Filename = String.valueOf(uploader)+"#"+String.valueOf(taskid);
                QiniuCloudUtil.put64image((File)file,Filename);
                //对Task的完成情况进行改变
                org.csu.workmaster_v1.Entity.File filesave = new org.csu.workmaster_v1.Entity.File(Url.url+"/"+Filename,uploader,taskid);
                fileDao.saveFile(filesave);

                //如果不需要File文件可删除
                if(file.exists()){
                    file.delete();
                }
                return 1;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getuserimage(long userid) {
        User user = userDao.findUserByUserId(userid);
        return user.getAvatar();
    }

    @Override
    public String getgroupimage(long groupid) {
        Group group = groupDao.findGroupByGroupId(groupid);
        return group.getGroupavatar();
    }

    @Override
    public List<String> getTaskFile(long taskid) {
        Task task = taskDao.findTaskBytaskid(taskid);
        List<String> filelist = new ArrayList<>();
        List<Long> list  = task.getUserdone();
        for (long userid :list){
            filelist.add(Url.url+"/"+String.valueOf(userid)+"#"+String.valueOf(taskid));
        }
        return filelist;
    }
}
