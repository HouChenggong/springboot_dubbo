package com.mydubbo.common.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.mydubbo.common.entity.UserCommon;
import com.mydubbo.common.service.CacheService;
import com.mydubbo.common.service.UserCommonService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author xiyou
 * 1. 主要测试读写分离、主从复制、数据源切换
 * 2. 测试fastjson缓存、存储数据
 */
@RestController
public class UserCommonController {


    @Reference(version = "${demo.service.version}")
    private UserCommonService userCommonService;


    @Reference(version = "${demo.service.version}")
    private CacheService cacheService;

    @GetMapping("/user/add")
    public Boolean sayHello() {

        UserCommon userCommon = new UserCommon();
        userCommon.setAge(111);
        userCommon.setName(new Date().toString());
        return userCommonService.addUser(userCommon);
    }

    @GetMapping("/user/list2")
    public List<UserCommon> sel2() {
        cacheService.setCacheToRedis("123", "撒旦阿松大2232", 6000L);
        String re = String.valueOf(cacheService.getCacheByKey("123"));
        System.out.println(re + cacheService.getExpire("123"));
        return userCommonService.selectUserFromDsGroup("slave2");
    }


    @GetMapping("/user/list1")
    public List sel1() {
        return userCommonService.selectUsersFromDs("slave1");
    }
}
