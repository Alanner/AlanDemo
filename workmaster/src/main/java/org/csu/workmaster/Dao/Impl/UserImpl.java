package org.csu.workmaster.Dao.Impl;

import org.csu.workmaster.Dao.UserDao;
import org.csu.workmaster.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserImpl implements UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;
     //更新对象
    @Override
    public void updateUser(User user) {
        mongoTemplate.save(user);
    }
     //删除对象
    @Override
    public void deleteUserById(Long id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,User.class);
    }

     //创建对象
    @Override
    public void saveUser(User user) {
        mongoTemplate.save(user);
    }

    // 根据用户Id查询对象
    @Override
    public User findUserByUserId(long id) {
        Query query=new Query(Criteria.where("id").is(id));
        return  mongoTemplate.findOne(query , User.class);
    }
    //更新密码
    @Override
    public int updatePsw(long userId,String psw) {
        Query query=new Query(Criteria.where("id").is(userId));
        User user =  mongoTemplate.findOne(query , User.class);
        if(user!= null){
            user.setUserpassword(psw);
            return 1;
        }
        return 0;
    }
    //更新头像
    @Override
    public int updateAvatar(long userId, String Avator) {
        Query query=new Query(Criteria.where("id").is(userId));
        User user =  mongoTemplate.findOne(query , User.class);
        if(user!= null){
           user.setAvatar(Avator);
           return 1;
        }
        return 0;
    }
    //更新组信息
    @Override
    public void updateGroup(long userId,long GroupId) {
        Query query=new Query(Criteria.where("id").is(userId));
        User user =  mongoTemplate.findOne(query , User.class);
        if(user!=null) {
            List GroupList = user.getGrouplist();
            GroupList.add(GroupId);
            Update update = Update.update("grouplist", GroupList);
            mongoTemplate.upsert(query, update, User.class);
        }
    }

    @Override
    public User findUserByusername(String username) {
        Query query=new Query(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query , User.class);
    }

    @Override
    public User findUserByStudentid(String studentid) {
        Query query=new Query(Criteria.where("studentid").is(studentid));
        return mongoTemplate.findOne(query , User.class);
    }
}