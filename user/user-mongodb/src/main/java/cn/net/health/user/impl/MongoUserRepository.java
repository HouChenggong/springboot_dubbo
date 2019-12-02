package cn.net.health.user.impl;

import cn.net.health.user.entity.MongoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/2 13:55
 */
@Component
public class MongoUserRepository {


    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * //TODO:新增：先插入数据，再插入MongoDB（也可以不插入数据库，而直接插入MongoDB）
     *
     * @param user
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(final MongoUser user) throws Exception {

        mongoTemplate.save(user);
    }

    /***
     * //TODO:批量新增
     * @param list
     * @throws Exception
     */
    public void saveAll(final List<MongoUser> list) throws Exception {
        if (list != null && !list.isEmpty()) {
            mongoTemplate.insertAll(list);
        }
    }

    /**
     * //TODO:查询-列表
     *
     * @return
     * @throws Exception
     */
    public List<MongoUser> queryAll() throws Exception {
        return mongoTemplate.findAll(MongoUser.class);
    }

    /**
     * //TODO:查询-主键查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    public MongoUser queryById(final String id) throws Exception {
        return mongoTemplate.findById(id, MongoUser.class);
    }

    /**
     * //TODO:查询-带条件查询
     *
     * @param name
     * @param column 要查询的字段
     * @return
     * @throws Exception
     */
    public List<MongoUser> queryByName(final String name, String column) throws Exception {
        Criteria criteria = Criteria.where(column).is(name);
        return mongoTemplate.find(Query.query(criteria), MongoUser.class);
    }

    /**
     * //TODO:更新
     *
     * @param user
     * @param byColumn 根据什么字段更新
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(final MongoUser user, String byColumn) throws Exception {
        Query query = Query.query(Criteria.where(byColumn).is(user.getUserId()));
        //下面更新的字段必须是原本有的字段,如果没有相关的字段，mongo会自动添加字段，因为mongo存的是JSON类型的document
        Update update = Update.update("userName", user.getUserName()).set("phone", user.getPhone() + "phone").set("qq", user.getQq() + "qq");
        mongoTemplate.updateFirst(query, update, MongoUser.class);
    }
}