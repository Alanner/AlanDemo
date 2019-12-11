package org.csu.workmaster_v1.web;

import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.csu.workmaster_v1.Dao.*;
import org.csu.workmaster_v1.Entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/user")
@Api(tags = "1.1", description = "用户管理", value = "用户管理")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private org.csu.workmaster_v1.service.MailService mailService;
    @Autowired
    private GroupDao groupDao;

    @GetMapping("/login")
    @ApiOperation(value = "登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = ApiDataType.STRING, paramType = ApiParamType.QUERY),
            @ApiImplicitParam(name = "userpassword", value = "密码", dataType = ApiDataType.STRING, paramType = ApiParamType.QUERY),
    })
    public Map<String,Object> login(String username, String userpassword ,HttpServletRequest request) {
        System.out.println(username+"***"+userpassword);
        User user = userDao.findUserByStudentid(username);
        System.out.println(user);
        Map<String, Object> map = new HashMap<>();
        if(user!=null){
            if(user.getUserpassword().equals(userpassword)) {
                if(user.getVerification_status()==0){
                    map.put("status",2);
                    map.put("message", "please verify the mail");
                }else{
                    map.put("status", 1);
                    map.put("message", "success");
                    map.put("user",user);
                    request.getSession().setAttribute("studentid", user.getStudentId());//设置session
                }
            }else {
                map.put("status",0);
                map.put("message", "password invalid");
            }
        }else {
                map.put("status",0);
                map.put("message", "user don't exit");
        }
        return map;
    }

    @PostMapping("/registe")
    @ApiOperation(value = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentid", value = "学号", dataType = ApiDataType.STRING, paramType = ApiParamType.HEADER),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
            @ApiImplicitParam(name = "userpassword", value = "密码", dataType = ApiDataType.STRING, paramType = ApiParamType.FORM),
    })
    public Map<String, Object> post(String studentid,String username,String userpassword, HttpServletRequest request) {
        User user = userDao.findUserByStudentid(studentid);
        Map<String, Object> map = new HashMap<>();
        if(user !=null){
            map.put("message","user has exist");
            map.put("status",0);
        }else{
            map.put("status",1);
            map.put("message","success");
            userDao.saveUser(new User(studentid,userpassword,username));
            //发送邮件
            mailService.sendLoginMail(studentid);
        }
        return map;
    }

    @GetMapping("/registe/{id}")
    @ApiOperation(value = "修改用户验证状态")
    public void put(@PathVariable String id ) {
        User user = userDao.findUserByStudentid(id);
        System.out.println(user);
        user.setVerification_status(1);
        userDao.saveUser(user);
        System.out.println(user);
    }
    /*@GetMapping("/getUser")//返回用户的消息，任务，通知以及群列表
    public Map<String, Object> getUser(String StudentId){
        Map<String, Object> map = new HashMap<>();
        User user = userDao.findUserByStudentid(StudentId);

        List<Message> messagelist = new ArrayList<>();
        List<Task> tasklist = new ArrayList<>();
        List<Long> groupList = user.getGrouplist();
        List<Notification> notifications = notificationDao.findNotificationByStudentid(StudentId);
        for (long groupid:groupList ) {
            List<Task> tasks = taskDao.findTaskByGroupid(groupid);
            List<Message> messages = messageDao.findMessageByGroupid(groupid);
            if(tasks.size()>0){
                tasklist.addAll(tasks);
                messagelist.addAll(messages);
            }
        }
        List<Group> grouplist = new ArrayList<>();
        Group group;
        for (Long groupId : groupList) {
            group = groupDao.findGroupByGroupId(groupId);
            grouplist.add(group);
        }
        if(user !=null){
            map.put("messagelist",messagelist);
            map.put("tasklist",tasklist);
            map.put("notificationlist",notifications);
            map.put("grouplist",grouplist);
            map.put("status",1);
        }else{
            map.put("status",0);
        }
        return map;
    }*/
    @GetMapping("/getUser")//返回用户的消息，任务，通知以及群列表
    public Map<String, Object> getUser(String StudentId){
        Map<String, Object> map = new HashMap<>();
        User user = userDao.findUserByStudentid(StudentId);

        List<Message> messagelist = new ArrayList<>();
        List<Task> tasklist = new ArrayList<>();
        List<Long> groupList = user.getGrouplist();
        List<Notification> notifications = notificationDao.findNotificationByStudentid(StudentId);
        for (long groupid:groupList ) {
            List<Task> tasks = taskDao.findTaskByGroupid(groupid);
            List<Message> messages = messageDao.findMessageByGroupid(groupid);
            if(tasks.size()>0 ){
                tasklist.addAll(tasks);
            }
            if(messages.size()>0){
                messagelist.addAll(messages);
            }
        }
        List<Group> grouplist = new ArrayList<>();
        Group group;
        for (Long groupId : groupList) {
            group = groupDao.findGroupByGroupId(groupId);
            grouplist.add(group);
        }
        if(user !=null){
            map.put("messagelist",messagelist);
            map.put("tasklist",tasklist);
            map.put("notificationlist",notifications);
            map.put("grouplist",grouplist);
            map.put("status",1);
        }else{
            map.put("status",0);
        }
        return map;
    }

    @PostMapping("/changepsw")//改变用户密码
    public Map<String,Object> changePsw(String StudenId,String psw){
        Map<String, Object> map = new HashMap<>();
        try{
            User user = userDao.findUserByStudentid(StudenId);
            user.setUserpassword(psw);
            userDao.saveUser(user);
            map.put("status",1);
        }catch (Exception ex){
            map.put("status",0);
        }
        return map;
    }
    //------头像暂放-----//
    @PostMapping("/changeAvatar")//改变用户头像的url
    public Map<String,Object> changeAvator(String StudenId,String Avatar){
        System.out.println("1--1//--"+StudenId+"***"+Avatar);
        Map<String, Object> map = new HashMap<>();
        try{
            User user = userDao.findUserByStudentid(StudenId);
            System.out.println(user==null);
            if( Avatar.length()>0 && user!=null){
                user.setAvatar(Avatar);
                userDao.saveUser(user);
                map.put("status",1);
                System.out.println(user);
            }else{
                map.put("status",0);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            map.put("status",0);
        }
        return map;
    }

    @GetMapping("/getbaseuser")//单纯得到这个用户
    public Map<String,Object> getBaseUser(String StudenId){
        System.out.println(StudenId+"/////");
        Map<String, Object> map = new HashMap<>();
        try{
            User user = userDao.findUserByStudentid(StudenId);
            System.out.println(user);
            map.put("user",user);;
            map.put("status",1);
        }catch(Exception ex){
            map.put("status",0);
        }
        return map;
    }

    @GetMapping("/getlistusers")//得到一堆用戶
    public Map<String,Object> getlistusers(String StudentId, int number){
        Map<String ,Object> map =new HashMap<>();
        List<User> list = new ArrayList<>();
        for (int i = 0 ; i< number ;i ++){
            long student = Long.valueOf(StudentId)+i;
            User user = userDao.findUserByStudentid(String.valueOf(student));
            if(user!=null){
                list.add(user);
            }
        }
        map.put("users",list);
        if(list.size()>0){
            map.put("status",1);
        }else{
            map.put("status",0);
        }
        return  map;
    }

   /* @PostMapping("/changeAvatar")
    public Map<String,Object> changeAvator(String StudenId,String Avatar){
        Map<String, Object> map = new HashMap<>();
        try{
            if( Avatar.length()>0 && userDao.findUserByUserId(Long.valueOf(StudenId))!=null){
                userDao.updateAvatar(Long.valueOf(StudenId),Avatar);
                map.put("status",1);
            }else{
                map.put("status",0);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            map.put("status",0);
        }
        return map;
    }*/
}