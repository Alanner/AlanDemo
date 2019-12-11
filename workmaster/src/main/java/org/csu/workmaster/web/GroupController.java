package org.csu.workmaster.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.workmaster.Dao.*;
import org.csu.workmaster.Entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin
@RestController
@RequestMapping("/group")
@Api(tags = "1.1", description = "群组管理", value = "群组管理")
public class GroupController {

    private static final Logger log = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private FileDao filedao;


    @PostMapping("/creat")
    public Map<String ,Object> creat(String studentId,String groupname,String description,String Avatar,String groupid){
        if(groupid == ""){
            groupid = String.valueOf(new Random().nextLong());
        }
        if(Avatar == ""){
            Avatar = "http://pf8r5ullw.bkt.clouddn.com/-1257351316062730504";
        }
        Map<String ,Object> map = new HashMap<>();
        try{
            User user = userDao.findUserByStudentid(studentId);
            Group group = new Group(Long.valueOf(groupid),groupname,description,Avatar);
            user.getGrouplist().add(group.getId());
            group.getUserlist().add(user.getId());
            userDao.updateGroup(user.getId(),group.getId());
            groupDao.saveGroup(group);
            map.put("status",1);
        }catch (Exception ex){
            map.put("status",0);
        }
        return map;
    }

    @GetMapping("/adduser")
    public Map<String, Object> adduser(String studentId, long groupid){
        Map<String ,Object> map = new HashMap<>();
        try {
            User user = userDao.findUserByStudentid(studentId);
            Group group = groupDao.findGroupByGroupId(groupid);
            System.out.println((group==null )+ "****"+ (user==null));

            if( user != null && group != null ) {
                if(!user.getGrouplist().contains(group.getId())){
                    user.getGrouplist().add(group.getId());
                }
                if(!group.getUserlist().contains(user.getId())){
                    group.getUserlist().add(user.getId());
                }
                map.put("status",1);
                userDao.saveUser(user);
                groupDao.saveGroup(group);
            }else{
                map.put("status",0);
            }
        }catch (Exception ex){
            map.put("status",0);
        }
        return  map;
    }

    @GetMapping("/deleteuser")
    public Map<String, Object> deleteuser(String studentId, Long groupId){
        Map<String ,Object> map = new HashMap<>();
        try {
            User user = userDao.findUserByStudentid(studentId);
            Group group = groupDao.findGroupByGroupId(groupId);
            if( user != null && group != null ) {
                user.getGrouplist().remove(group.getId());
                group.getUserlist().remove(user.getId());
                map.put("status",1);
                userDao.updateUser(user);
                groupDao.updateGroup(group);
            }else{
                map.put("status",0);
            }
        }catch (Exception ex){
            map.put("message","there is an Exception in the server");
        }
        return  map;
    }

/*    @GetMapping("/addusers")
    @ApiOperation(value = "邀请一堆人加入，返回该区间人的信息")
    public Map<String, Object> adduser(String studentId, String groupName, int number){
        Map<String ,Object> map = new HashMap<>();
        try {
            Group group = groupDao.findGroupByGroupName(groupName);
            int numStudent = Integer.valueOf(studentId);
            for ( int i = 0;i < number;i++){
                User user = userDao.findUserByStudentid(String.valueOf(numStudent+i));
                if( user != null && group != null ) {
                    user.getGrouplist().add(group.getGroupid());
                    group.getUserlist().add(user.getId());
                    userDao.updateUser(user);
                    groupDao.updateGroup(group);
                }
            }
            map.put("status",1);
            map.put("message","success");
            map.put("users",group.getUserlist());
        }catch (Exception ex){
            map.put("message","there is an Exception in the server");
        }
        return  map;
    }*/

    @GetMapping("/getGroup")
    @ApiOperation(value = "获取群的详细信息")
    public Map<String, Object> getgroup(String groupId){
        System.out.println(groupId);
        Map<String ,Object> map = new HashMap<>();
        try {
            Group group = groupDao.findGroupByGroupId(Long.valueOf(groupId));
            List<Long> userlist = group.getUserlist();
            List<User> members = new ArrayList<>();
            User user;
            for (Long userId : userlist){
               user = userDao.findUserByUserId(userId);
               members.add(user);
            }
            map.put("status",1);
            map.put("notices",messageDao.findMessageByGroupid(Long.valueOf(groupId)) == null? new ArrayList<Message>() : messageDao.findMessageByGroupid(Long.valueOf(groupId)));
            map.put("tasks",taskDao.findTaskByGroupid(Long.valueOf(groupId)) == null? new ArrayList<Task>() : taskDao.findTaskByGroupid(Long.valueOf(groupId)));
            map.put("groupInfo",group);
            map.put("members",members);
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("status",0);
        }
        return  map;
    }
}
