package org.csu.workmaster.web;


import io.swagger.annotations.ApiOperation;
import org.csu.workmaster.Dao.GroupDao;
import org.csu.workmaster.Dao.MessageDao;
import org.csu.workmaster.Dao.UserDao;
import org.csu.workmaster.Entity.Group;
import org.csu.workmaster.Entity.Message;
import org.csu.workmaster.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin
@RestController
@RequestMapping("/Message")
public class MessageController {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;

    @PostMapping("/upload")
    @ApiOperation(value = "发布通知")
    public Map<String, Object> post(long groupId , String messagename, String messagecontent, String messageabstract, long messagepublisher) {
        User user = userDao.findUserByStudentid(String.valueOf(messagepublisher));
        Message message = new Message(groupId,messagename,messageabstract,messagecontent,messagepublisher,user.username);
        Map<String ,Object> map = new HashMap<String ,Object>();
        try {
            messageDao.saveMessage(message);
            map.put("status",1);
        }catch (Exception ex ){
            map.put("status",0);
        }
        return map;
    }

 /*   @GetMapping("/changestatus")
    public Map<String, Object> changestatus(String StudentId,long messageid){
        System.out.println();
        Map<String, Object> map = new HashMap<>();
        Message message = messageDao.findMessageByMessageid(messageid);
        try {
            message.getUserreaded().add(userDao.findUserByStudentid(StudentId).getId());
            messageDao.saveMessage(message);
            map.put("status",1);
        }catch (Exception ex ){
            map.put("status",0);
        }
        return map;
    }*/
    @GetMapping("/changestatus")//这个实际上是id 64位
    public Map<String, Object> changestatus(String StudentId,long messageid){
        Map<String, Object> map = new HashMap<>();
        Message message = messageDao.findMessageByMessageid(messageid);
        try {
            message.getUserreaded().add(Long.valueOf(StudentId));
            messageDao.saveMessage(message);
            map.put("status",1);
        }catch (Exception ex ){
            ex.printStackTrace();
            map.put("status",0);
        }
        return map;
    }
    @GetMapping("/getgroupmessages")
    //返回该用户所有的消息
    public Map<String, Object> getmessages(long groupid){
        System.out.println(groupid);
        Map<String, Object> map = new HashMap<>();
        List<Message> list = messageDao.findMessageByGroupid(groupid);
        try {
            map.put("status",1);
            map.put("messages",list);
        }catch (Exception ex ){
            map.put("status",0);
        }
        return map;
    }

}
