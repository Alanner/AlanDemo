package org.csu.workmaster_v1.Dao.Impl;

import com.mongodb.Mongo;
import org.csu.workmaster_v1.Dao.TaskDao;
import org.csu.workmaster_v1.Entity.Group;
import org.csu.workmaster_v1.Entity.Message;
import org.csu.workmaster_v1.Entity.Task;
import org.csu.workmaster_v1.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskImpl implements TaskDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     */
    @Override
    public void saveTask(Task task) {
        mongoTemplate.save(task);
    }

    /**
     * 根据任务名查询对象
     */
    @Override
    public Task findTaskByTaskName(String taskName) {
        Query query = new Query(Criteria.where("taskName").is(taskName));
        Task task = mongoTemplate.findOne(query , Task.class);
        return task;
    }

    /**
     * 更新任务对象
     */
    @Override
    public void updateTask(Task task) {
        Query query=new Query(Criteria.where("id").is(task.getId()));
        Task t =  mongoTemplate.findOne(query , Task.class);
        t.setUserdone(task.getUserdone());
    }

    /**
     * 删除对象
     */
    @Override
    public void deleteTaskById(Long taskId) {
        Query query = new Query(Criteria.where("id").is(taskId));
        mongoTemplate.remove(query,Task.class);
    }

    @Override
    public List<Task> findTaskByGroupid(Long groupid) {
        Query query = new Query(Criteria.where("groupid").is(groupid));
        List<Task> list = mongoTemplate.find(query, Task.class);
        return list;
    }

    @Override
    public Task findTaskBytaskid(long taskid) {
        Query query = new Query(Criteria.where("id").is(taskid));
        Task task = mongoTemplate.findOne(query , Task.class);
        return task;
    }
}
