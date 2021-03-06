

package org.csu.workmaster.Dao;

import org.csu.workmaster.Entity.Message;

import java.util.List;

public interface MessageDao {

    public void saveMessage(Message message);
    public Message findMessageByMessageName(String MessageName);
    public void updateMessage(Message message);
    public void deleteMessageById(Long id);
    //public List<Message> findMessageByStudentid(String studentID);
    public Message findMessageByMessageid(long messageid);
    public List<Message> findMessageByGroupid(long groupid);
}


