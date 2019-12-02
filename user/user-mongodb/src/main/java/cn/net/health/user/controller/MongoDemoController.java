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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

        list = mongoUserRepository.queryAll();
        log.info("---查询所有：{}---", list);
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


}