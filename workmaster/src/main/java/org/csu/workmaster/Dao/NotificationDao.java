package org.csu.workmaster.Dao;

import org.csu.workmaster.Entity.Notification;

import java.util.List;

public interface NotificationDao {

    public void saveNotification(Notification notification);
    public Notification findNotificationByNotificationId(Long id);
    public void updateNotification(Notification notification);
    public void deleteNotificationById(Long id);
    public List<Notification> findNotificationByStudentid(String Studentid);
    public List<Notification> findNotificationByGroupid(long GroupId);
//
}
