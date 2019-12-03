package cn.net.health.user.impl;

import cn.net.health.user.entity.MongoUser;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据列删除记录
     *
     * @param userId
     * @param column
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(final String userId, String column) throws Exception {
        Query query = Query.query(Criteria.where(column).is(userId));
        mongoTemplate.remove(query, MongoUser.class);
    }


    /**
     * //TODO:分页查询
     *
     * @param pageNo       页码
     * @param pageSize     限制
     * @param selectColumn 要查询的字段
     * @param columnValue  字段的值
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryPage(Integer pageNo, final Integer pageSize, String selectColumn, String columnValue) throws Exception {
        Map<String, Object> resMap = Maps.newHashMap();
        if (pageNo <= 0) {
            pageNo = 1;
        }
        Integer pageStart = (pageNo - 1) * pageSize;

        //TODO:正常不带条件的分页
        //Query query=Query.query(Criteria.where("isActive").is(1)).skip(pageStart).limit(pageSize);
        //return mongoTemplate.find(query,MongoUser.class);


        //TODO:正常带条件的分页-按照code倒序
        Query query = Query.query(Criteria.where(selectColumn).is(columnValue)).skip(pageStart).limit(pageSize)
                .with(Sort.by(Sort.Direction.DESC, "code"));
        //TODO:查询出目前MongoDB中该集合“mongoUser”总的数据条目
        Long total = mongoTemplate.count(query, MongoUser.class);
        List<MongoUser> list = mongoTemplate.find(query, MongoUser.class);

        resMap.put("total", total);
        resMap.put("list", list);

        return resMap;
    }
}