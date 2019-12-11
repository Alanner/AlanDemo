package org.csu.workmaster.Dao.Impl;

import org.csu.workmaster.Dao.NotificationDao;
import org.csu.workmaster.Entity.Message;
import org.csu.workmaster.Entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationImpl implements NotificationDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveNotification(Notification notification) {
        mongoTemplate.save(notification);
    }

    @Override
    public Notification findNotificationByNotificationId(Long notificationId) {
        Query query = new Query(Criteria.where("id").is(notificationId));
        Notification notification = mongoTemplate.findOne(query, Notification.class);
        return notification;
    }

    @Override
    public void updateNotification(Notification notification) {
        Query query = new Query(Criteria.where("id").is(notification.getId()));
        Update update = new Update().set("notifictionContent", notification.getNotifictioncontent()).set("id", notification.getGroupid()).set("time", notification.getTime());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, Notification.class);
    }

    @Override
    public void deleteNotificationById(Long notificationId) {
        Query query = new Query(Criteria.where("id").is(notificationId));
        mongoTemplate.remove(query,Notification.class);
    }
    @Override
    public List<Notification> findNotificationByStudentid(String Studentid){
        Query query = new Query(Criteria.where("receiver").is(Studentid));
        List<Notification> list = mongoTemplate.find(query, Notification.class);
        return list;
    }
    @Override
    public List<Notification> findNotificationByGroupid(long GroupId){
        Query query = new Query(Criteria.where("groupid").is(GroupId));
        List<Notification> list = mongoTemplate.find(query, Notification.class);
        return list;
    }
}
