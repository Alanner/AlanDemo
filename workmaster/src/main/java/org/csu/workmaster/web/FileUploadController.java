package org.csu.workmaster.web;
import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.csu.workmaster.Dao.FileDao;
import org.csu.workmaster.Dao.GroupDao;
import org.csu.workmaster.Dao.TaskDao;
import org.csu.workmaster.Dao.UserDao;
import org.csu.workmaster.Entity.Group;
import org.csu.workmaster.Entity.Task;
import org.csu.workmaster.Entity.User;
import org.csu.workmaster.Util.QiniuCloudUtil;
import org.csu.workmaster.Util.Url;
import org.csu.workmaster.service.FileService;
import org.csu.workmaster.service.QiniuFileService;
import org.csu.workmaster.service.impl.QiniuFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


//文件传输控制类
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private QiniuFileService qiniuFileService;




    @PostMapping("/uploaduserimage")
    public Map<String ,Object> uploadtuserimage(@RequestParam("file") MultipartFile multipartfile, HttpServletRequest request){
        Map<String ,Object> map =new HashMap<>();
        try{
            long userid = Long.valueOf(request.getParameter("userid"));
            String result = qiniuFileService.uploaduserImage(multipartfile,userid);
            map.put("url",result);
            map.put("status",1);
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("status",0);
        }
        return map;
    }

    @RequestMapping("/uploadgroupimage")
    public Map<String ,Object> uploadtgroupimage(@RequestParam("file") MultipartFile multipartfile, HttpServletRequest request){
        Map<String ,Object> map =new HashMap<>();
        try{
            long groupid = new Random().nextLong();
            String result = qiniuFileService.uploadergroupImage(multipartfile,groupid);
            map.put("url",result);
            map.put("groupid",String.valueOf(groupid));
            System.out.println(groupid);
            map.put("status",1);
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("status",0);
        }
        return map;
    }
/*
    @RequestMapping("/upload")
    public JSONObject upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        JSONObject result = new JSONObject();
        // 姓名
        String name = request.getParameter("name");
        System.out.println("姓名：" + name);
        // 文件名
        String fileName = file.getOriginalFilename();
        System.out.println("文件名： " + fileName);

        // 文件后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("文件后缀名： " + suffixName);
        // 重新生成唯一文件名，用于存储数据库
        String newFileName = UUID.randomUUID().toString()+suffixName;
        System.out.println("新的文件名： " + newFileName);
        //创建文件
        File dest = new File(filePath + newFileName);

        Map map = new HashMap();
        map.put("filePath", dest.getAbsolutePath());
        map.put("name", name);
        try {
            file.transferTo(dest);
            result.put("success", true);
            result.put("data", map);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (JSONObject) result.put("success", false);
    }*/
}