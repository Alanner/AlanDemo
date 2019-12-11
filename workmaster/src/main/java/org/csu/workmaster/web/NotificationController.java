package org.csu.workmaster.web;

import io.swagger.annotations.ApiOperation;
import org.csu.workmaster.Dao.NotificationDao;
import org.csu.workmaster.Dao.UserDao;
import org.csu.workmaster.Entity.Message;
import org.csu.workmaster.Entity.Notification;
import org.csu.workmaster.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin
@RestController
@RequestMapping("/Notification")
public class NotificationController {
    @Autowired
    NotificationDao notificationDao;
    @Autowired
    UserDao userDao;

    @GetMapping("/invitegroup")
    @ApiOperation(value = "邀请一人加入，需要双方学号，邀请内容")
    public Map<String ,Object> invite(long groupid, String studentid, String inviterstudentid,String notificationcontent){
        Map<String ,Object> map = new HashMap<>();
        try{
           /* User sender = userDao.findUserByStudentid(inviterstudentid);
            User receiver = userDao.findUserByStudentid(studentid);*/
            Notification notification = new Notification(inviterstudentid,studentid,notificationcontent,groupid);
            notificationDao.saveNotification(notification);
            map.put("message","success");
            map.put("status",1);
        }catch (Exception ex){
            map.put("message","failed");
            map.put("status",0);
        }
        return map;
    }
    @GetMapping("/invitemanytogroup")
    @ApiOperation(value = "邀请多人加入，需要双方学号，用戶數量，邀请内容")
    public Map<String ,Object> invite(long groupid, String studentid, String inviterstudentid,String notificationcontent,int number){
        Map<String ,Object> map = new HashMap<>();
        try{
            User sender = userDao.findUserByStudentid(inviterstudentid);
            User receiver;
            for(int i =0 ;i < number ;i++){
                receiver= userDao.findUserByStudentid(String.valueOf(Integer.valueOf(studentid)+i));
                Notification notification = new Notification(inviterstudentid,studentid,notificationcontent,groupid);
                notificationDao.saveNotification(notification);
            }
            map.put("status",1);
        }catch (Exception ex){
            map.put("status",0);
        }
        return map;
    }

    @GetMapping("/changestatus")
    public Map<String, Object> changestatus(String StudentId,long notificationid){
        System.out.println(StudentId + "  " + notificationid);
        Map<String, Object> map = new HashMap<>();
        Notification notification = notificationDao.findNotificationByNotificationId(notificationid);
        try {
            notification.setNotificationstatus(1);
            notificationDao.saveNotification(notification);
            map.put("status",1);
            map.put("message","success");
        }catch (Exception ex ){
            map.put("message","Exception");
            map.put("status",0);
        }
        return map;
    }

    @GetMapping("/changestatustwo")
    public Map<String, Object> changestatustwo(String StudentId,long notificationid){
        System.out.println(StudentId + "  " + notificationid);
        Map<String, Object> map = new HashMap<>();
        Notification notification = notificationDao.findNotificationByNotificationId(notificationid);
        try {
            notification.setNotificationstatus(2);
            notificationDao.saveNotification(notification);
            map.put("status",1);
            map.put("message","success");
        }catch (Exception ex ){
            map.put("message","Exception");
            map.put("status",0);
        }
        return map;
    }
}
