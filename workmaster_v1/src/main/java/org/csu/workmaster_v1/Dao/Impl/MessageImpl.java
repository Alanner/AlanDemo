package org.csu.workmaster_v1.Dao.Impl;


import org.csu.workmaster_v1.Dao.MessageDao;
import org.csu.workmaster_v1.Entity.Message;
import org.csu.workmaster_v1.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageImpl implements MessageDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     *
     */
    @Override
    public void saveMessage(Message message) {
        mongoTemplate.save(message);
    }

    /**
     * 根据消息名查询对象
     *
     * @return
     */
    @Override
    public Message findMessageByMessageName(String messageName) {
        Query query = new Query(Criteria.where("messageName").is(messageName));
        Message message = mongoTemplate.findOne(query, Message.class);
        return message;
    }

    /**
     * 更新消息对象
     *
     */
    @Override
    public void updateMessage(Message message) {
        Query query = new Query(Criteria.where("id").is(message.getId()));
        Update update = new Update().set("messageName", message.getMessagename()).set("messageContent", message.getMessagecontent()).set("time", message.getTime());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, Message.class);
    }

    /**
     * 删除对象
     */
    @Override
    public void deleteMessageById(Long messageId){
        Query query = new Query(Criteria.where("id").is(messageId));
        mongoTemplate.remove(query,Message.class);
    }
   /* @Override
    public List<Message> findMessageByStudentid(String studentId){
        Query query = new Query(Criteria.where("studentid").is(studentId));
        List<Message> list = mongoTemplate.find(query, Message.class);
        return list;
    }*/

    @Override
    public Message findMessageByMessageid(long messageid) {
        Query query = new Query(Criteria.where("id").is(messageid));
        Message message = mongoTemplate.findOne(query, Message.class);
        return message;
    }

    @Override
    public List<Message> findMessageByGroupid(long groupid) {
        Query query = new Query(Criteria.where("groupid").is(groupid));
        List<Message> list = mongoTemplate.find(query, Message.class);
        return list;
    }
}



