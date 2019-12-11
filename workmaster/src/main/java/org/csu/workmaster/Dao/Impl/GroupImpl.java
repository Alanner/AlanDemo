package org.csu.workmaster.Dao.Impl;

import com.mongodb.Mongo;
import com.mongodb.QueryBuilder;
import org.csu.workmaster.Dao.GroupDao;
import org.csu.workmaster.Entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class GroupImpl implements GroupDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建群组
     */
    @Override
    public void saveGroup(Group group) {
        mongoTemplate.save(group);

    }

    /**
     * 根据群组Id查询对象
     * @return
     */
    @Override
    public Group findGroupByGroupId(long id) {
        Query query=new Query(Criteria.where("id").is(id));
        Group group= mongoTemplate.findOne(query,Group.class);
        return group;
    }

    /**
     * 根据groupId更新对象
     */
    @Override
    public void updateGroup(Group group) {
        Query query=new Query(Criteria.where("id").is(group.getId()));
        Update update= new Update().set("groupname",group.getGroupname()).set("groupgescription", group.getGroupgescription());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update, Group.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,User.class);
    }

    /**
     * 删除群组
     */
    @Override
    public void deleteGroupByGroupId(long groupid) {
        Query query=new Query(Criteria.where("id").is(groupid));
        mongoTemplate.remove(query,Group.class);
    }

    @Override
    public Group findGroupByGroupName(String groupname){
        Query query=new Query(Criteria.where("groupname").is(groupname));
        Group group=mongoTemplate.findOne(query,Group.class);
        return group;
    }
}
