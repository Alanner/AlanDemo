package org.csu.workmaster_v1.Dao.Impl;

import org.csu.workmaster_v1.Dao.FileDao;
import org.csu.workmaster_v1.Entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileImpl implements FileDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     */
    @Override
    public void saveFile(File file) {
       mongoTemplate.save(file);
    }

    /**
     * 根据fileId查找对象
     */
    @Override
    public File findFileByFileId(long fileId) {
        Query query=new Query(Criteria.where("id").is(fileId));
        File file =  mongoTemplate.findOne(query , File.class);
        return file;
    }

    /**
     * 更新对象
     */
    @Override
    public void updateFile(File file) {
        Query query=new Query(Criteria.where("id").is(file.getId()));
        Update update= new Update().set("filehexid", file.getFilehexid());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update, File.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,User.class);
    }

    /**
     * 删除对象
     */
    @Override
    public void deleteFileByFileId(long fileId) {
        Query query=new Query(Criteria.where("id").is(fileId));
        mongoTemplate.remove(query,File.class);
    }

    @Override
    public File findFileByuploaderid(long userid) {
        Query query=new Query(Criteria.where("uploader").is(userid));
        File file=  mongoTemplate.findOne(query , File.class);
        return file;
    }
}
