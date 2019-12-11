package org.csu.workmaster_v1.service.impl;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.types.ObjectId;
import org.csu.workmaster_v1.Dao.FileDao;
import org.csu.workmaster_v1.Dao.TaskDao;
import org.csu.workmaster_v1.Dao.UserDao;
import org.csu.workmaster_v1.Entity.Task;
import org.csu.workmaster_v1.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Random;

@Component
public class FileServiceImpl  {
/*
    public static MongoClient mongoClient = new MongoClient("127.0.0.1",27017);//单例
    public static DB db = mongoClient.getDB("FileDonow");
    //单例即可，创建多个实例并无影响，内部间接使用了mongoClient作为通讯支撑
    public static GridFS gridFS = new GridFS(db);//可以指定bucket名字，默认值为“fs”

    @Autowired
    private FileDao fileDao;
    @Autowired
    private UserDao userDao;

    //上传任务文件，上传者id，任务id
    @Override
    public String upload(FileInputStream ins , long uploader, long taskid) {
        try{
            GridFSInputFile inputFile = gridFS.createFile(ins);
            //inputFile.setFilename("web-all.log");
            //inputFile.setContentType("text/plain");
            inputFile.save();
            ObjectId fileId = (ObjectId)inputFile.getId();//此ID，在客户端生成
            System.out.println(fileId.toHexString());//此字符串ID，可供application使用
            //保存文件
            fileDao.saveFile(new org.csu.workmaster_v1.Entity.File(fileId.toHexString(),uploader,taskid));

            return fileId.toHexString();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    //上传用户头像图片,图片文件，用户id
    @Override
    public String uploaduserimage(FileInputStream ins ,long userid){
        try{
            GridFSInputFile inputFile = gridFS.createFile(ins);
            inputFile.save();
            ObjectId fileId = (ObjectId)inputFile.getId();//此ID，在客户端生成
            System.out.println(fileId.toHexString());//此字符串ID，可供application使用
            //保存文件
            fileDao.saveFile(new org.csu.workmaster_v1.Entity.File(fileId.toHexString(),userid,1));
            return fileId.toHexString();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    //上传群组头像图片,群组图片，群组id
    @Override
    public String uploadgroupimage(FileInputStream ins ,long groupid){
        try{
            GridFSInputFile inputFile = gridFS.createFile(ins);
            inputFile.save();
            ObjectId fileId = (ObjectId)inputFile.getId();//此ID，在客户端生成
            System.out.println(fileId.toHexString());//此字符串ID，可供application使用
            //保存文件
            fileDao.saveFile(new org.csu.workmaster_v1.Entity.File(new Random().nextLong(),fileId.toHexString(),groupid,2));
            return fileId.toHexString();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public File downloadgroupimage(long groupid) {
        try {
            GridFSDBFile file = gridFS.find(new ObjectId(String.valueOf(fileDao.findFileByid(groupid,2).getFileid())));
            File filenew = new File(file.getFilename());
            //file.writeTo(new File("E:/nodevars.bat"));
            //此外还可以使用inputstream方式
            BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream(), 1024);
            OutputStream outputStream = new FileOutputStream(filenew);
            byte[] bytes = new byte[1024];
            while (true) {
                int count = inputStream.read(bytes);
                if (count < 1024) {
                    break;
                }
                outputStream.write(bytes, 0, count);
            }
            inputStream.close();
            outputStream.close();
            return filenew;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public File downloaduserimage(long userid) {
        try {
            GridFSDBFile file = gridFS.find(new ObjectId(String.valueOf(fileDao.findFileByid(userid,1).getFileid())));
            File filenew = new File(file.getFilename());
            //file.writeTo(new File("E:/nodevars.bat"));
            //此外还可以使用inputstream方式
            BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream(), 1024);
            OutputStream outputStream = new FileOutputStream(filenew);
            byte[] bytes = new byte[1024];
            while (true) {
                int count = inputStream.read(bytes);
                if (count < 1024) {
                    break;
                }
                outputStream.write(bytes, 0, count);
            }
            inputStream.close();
            outputStream.close();
            return filenew;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public File downloadgroupfile(long taskid) {
        return null;
    }*/
}
