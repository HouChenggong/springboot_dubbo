package cn.net.health.user.controller;


import cn.net.health.user.entity.MongoUser;
import cn.net.health.user.impl.MongoUserRepository;
import cn.net.health.user.utils.SnowFlake;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/11/6 11:45
 */
@RestController
@Api("test接口")
@Slf4j
@RequestMapping("/mongo/*")
public class MongoDemoController {

    @Autowired
    private MongoUserRepository mongoUserRepository;

    private SnowFlake snowFlake = new SnowFlake(2, 3);


    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")
    })

    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public String deleteUser(@RequestParam("userId") String userId) {
        System.out.println("deleteUser:::" + userId);
        return "OK";
    }

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")})
    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)

    public String queryUser(@RequestParam("userId") String userId) {
        System.out.println("queryUser:::" + userId);
        return "OK";

    }


    @ApiOperation(value = "查询用户", notes = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")})
    @RequestMapping(value = "/mongoInsertAndSelect", method = RequestMethod.GET)
    public void method1() throws Exception {
        log.info("---单元测试1---");
        List<MongoUser> list = mongoUserRepository.queryAll();
        log.info("---查询所有：{}---", list);

        MongoUser mongoUser = new MongoUser();
        mongoUser.setUserId(String.valueOf(snowFlake.makeNextId()));
        mongoUser.setUserName("testMongo");
        mongoUser.setNickName("223233");
        mongoUser.setPassWord("PASS");
        mongoUserRepository.save(mongoUser);
        log.info("-----插入成功-----");

//        list = mongoUserRepository.queryAll();
//        log.info("---查询所有：{}---", list);
    }

    @ApiOperation(value = "批量插入", notes = "批量插入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")
    })

    @RequestMapping(value = "/insertList", method = RequestMethod.POST)
    public String insertList(@RequestParam("userId") String userId) {
        List<MongoUser> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            MongoUser mongoUser = new MongoUser();
            mongoUser.setUserId(String.valueOf(snowFlake.makeNextId()));
            mongoUser.setUserName("name" + String.valueOf(snowFlake.makeNextId()));
            mongoUser.setNickName("223233");
            mongoUser.setPassWord("PASS");
            list.add(mongoUser);
        }
        try {
            mongoUserRepository.saveAll(list);
            log.info("-----批量插入成功-----");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            list = mongoUserRepository.queryAll();
            log.info("---查询所有：{}---", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "OK";
    }


    @ApiOperation(value = "根据主键id查询单个", notes = "根据name查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "name", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/selectByIdOrName", method = RequestMethod.GET)
    public void method3(String userId, String name) throws Exception {
        log.info("---单元测试3---");
        MongoUser entity = mongoUserRepository.queryById(userId);
        log.info("---根据主键id查询={},结果={}---", userId, entity);
        //column是要查询的字段
        String column = "userName";
        List<MongoUser> list = mongoUserRepository.queryByName(name, column);
        log.info("---根据姓名查询,name={},结果={}---", name, list);
    }

    @ApiOperation(value = "updateById", notes = "updateById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    public void method4(String userId) throws Exception {
        log.info("---单元测试4---");

        MongoUser entity = new MongoUser();
        entity = mongoUserRepository.queryById(userId);
        log.info("---根据主键id查询,结果={}---", entity);
        entity.setNickName("UPDATE");
        entity.setUserName("userUpdate");
        //根据userid更新
        mongoUserRepository.update(entity, "userId");
        entity = mongoUserRepository.queryById(userId);
        log.info("---根据主键id查询,结果={}---", entity);
    }


    /**
     * 删除
     *
     * @param userId
     * @throws Exception
     */
    @ApiOperation(value = "deleteById", notes = "deleteById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/deleteById")
    public void deleteById(String userId) throws Exception {
        log.info("---单元测试5-删除---");
        mongoUserRepository.delete(userId, "userId");
        MongoUser entity = mongoUserRepository.queryById(userId);
        log.info("---根据主键id查询,结果={}---", entity);
    }


    @ApiOperation(value = "批量插入", notes = "批量插入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "要插入几千条数据，注意1就是1000，10是一万", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/insertListNum", method = RequestMethod.POST)
    public String insertList(Integer num) {
        for (int j = 0; j < num; j++) {
            List<MongoUser> list = new ArrayList<>(1000);
            for (int i = 0; i < 1000; i++) {
                MongoUser mongoUser = new MongoUser();
                mongoUser.setUserId(String.valueOf(snowFlake.makeNextId()));
                mongoUser.setUserName("name_" + j * 1000 + i);
                mongoUser.setNickName("nick_name_" + j * 1000 + i);
                mongoUser.setPassWord("xiyou");
                list.add(mongoUser);
            }
            try {
                mongoUserRepository.saveAll(list);
                log.info("-----批量插入成功一千条记录-----");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "批量插入了：" + num * 1000 + "条记录";
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/selectByPage", method = RequestMethod.GET)
    public void selectByPage() throws Exception {
        log.info("---单元测试6-分页查询---");
        final Integer pageSize = 10;

        Integer pageNo = 1;
        Map<String, Object> resMap = mongoUserRepository.queryPage(pageNo, pageSize, "passWord", "xiyou");
        log.info("---分页查询pageNo={} pageSize={} 结果={}\n\n", pageNo, pageSize, resMap);

        pageNo = 2;
        resMap = mongoUserRepository.queryPage(pageNo, pageSize, "passWord", "xiyou");
        log.info("---分页查询pageNo={} pageSize={} 结果={}\n\n", pageNo, pageSize, resMap);

        pageNo = 3;
        resMap = mongoUserRepository.queryPage(pageNo, pageSize, "passWord", "xiyou");
        log.info("---分页查询pageNo={} pageSize={} 结果={}\n\n", pageNo, pageSize, resMap);

        pageNo = 800;
        resMap = mongoUserRepository.queryPage(pageNo, pageSize, "passWord", "xiyou");
        log.info("---分页查询pageNo={} pageSize={} 结果={}\n\n", pageNo, pageSize, resMap);

        pageNo = 1000;
        resMap = mongoUserRepository.queryPage(pageNo, pageSize, "passWord", "xiyou");
        log.info("---分页查询pageNo={} pageSize={} 结果={}\n\n", pageNo, pageSize, resMap);

        pageNo = 2000;
        resMap = mongoUserRepository.queryPage(pageNo, pageSize, "passWord", "xiyou");
        log.info("---分页查询pageNo={} pageSize={} 结果={}\n\n", pageNo, pageSize, resMap);
    }
}