package org.csu.workmaster.web;

import io.swagger.annotations.ApiOperation;
import org.csu.workmaster.Dao.FileDao;
import org.csu.workmaster.Dao.TaskDao;
import org.csu.workmaster.Dao.UserDao;
import org.csu.workmaster.Entity.Message;
import org.csu.workmaster.Entity.Task;
import org.csu.workmaster.service.QiniuFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin
@RestController
@RequestMapping("/Task")
public class TaskController {

    @Autowired
    private TaskDao taskdao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private QiniuFileService qiniuFileService;

    @PostMapping("/upload")
    @ApiOperation(value = "发布任务")
    public Map<String, Object> post(long groupId , String taskename, String taskcontent,long taskpublisher, String fileformat, String deadline,String tasktext) {
        Map<String ,Object> map = new HashMap<String ,Object>();
        try {
            Task task = new Task(groupId,taskename,taskcontent,taskpublisher,userDao.findUserByStudentid(String.valueOf(taskpublisher)).getUsername(),fileformat,deadline,tasktext);
            taskdao.saveTask(task);
            map.put("status",1);
        }catch (Exception ex ){
            ex.printStackTrace();
            map.put("status",0);
        }
        return map;
    }

    @GetMapping("/getgrouptask")
    //返回该用户所有的消息
    public Map<String, Object> gettask(long groupid){
        Map<String, Object> map = new HashMap<>();
        try {
            List<Task> list = taskdao.findTaskByGroupid(groupid);
            map.put("status",1);
            map.put("tasks",list);
        }catch (Exception ex ){
            map.put("status",0);
        }
        return map;
    }

    @RequestMapping("/uploadtask")
    public Map<String ,Object> uploadtest(@RequestParam("file") MultipartFile multipartfile, HttpServletRequest request){
        Map<String ,Object> map =new HashMap<>();
        long uploader = Long.valueOf(request.getParameter("uploader"));
        long taskid = Long.valueOf(request.getParameter("taskid"));
        int result = qiniuFileService.uploaderTaskfile(multipartfile,uploader,taskid);
        if(result == 1){
            Task task = taskdao.findTaskBytaskid(taskid);
            task.getUserdone().add(uploader);
            taskdao.saveTask(task);
        }
        map.put("status",result);
        return map;
    }

    @GetMapping("/gettasks")
    //返回该用户所有的消息
    public Map<String, Object> gettasks(String StudentId){
        Map<String, Object> map = new HashMap<>();
        List<Task> tasklist = new ArrayList<>();
        for (long groupid: userDao.findUserByStudentid(StudentId).getGrouplist()) {
            List<Task> tasks = taskdao.findTaskByGroupid(groupid);
            if(tasks.size()>0){
                tasklist.addAll(tasks);
            }
        }
        try {
            map.put("status",1);
            map.put("messages",tasklist);
        }catch (Exception ex ){
            map.put("status",0);
        }
        return map;
    }

    @GetMapping("/gettask")
    //返回该用户所有的消息
    public Map<String, Object> gettask(String taskid){
        System.out.println("--------------------------------------+++++++++++++++++++++++++");
        Map<String, Object> map = new HashMap<>();
        try {
            Task task = taskdao.findTaskBytaskid(Long.valueOf(taskid));
            map.put("status",1);
            map.put("task",task);
        }catch (Exception ex ){
            map.put("status",0);
        }
        return map;
    }
}