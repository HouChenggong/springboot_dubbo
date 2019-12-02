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
    public MongoUser queryById(final Integer id) throws Exception {
        return mongoTemplate.findById(id, MongoUser.class);
    }

    /**
     * //TODO:查询-带条件查询
     *
     * @param name
     * @return
     * @throws Exception
     */
    public List<MongoUser> queryByName(final String name) throws Exception {
        Criteria criteria = Criteria.where("name").is(name);
        return mongoTemplate.find(Query.query(criteria), MongoUser.class);
    }

    /**
     * //TODO:更新
     *
     * @param user
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(final MongoUser user) throws Exception {

        Query query = Query.query(Criteria.where("id").is(user.getUserId()));
        Update update = Update.update("name", user.getUserName()).set("code", user.getPhone()).set("email", user.getQq());
        mongoTemplate.updateFirst(query, update, MongoUser.class);
    }
}