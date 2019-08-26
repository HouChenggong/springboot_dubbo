package com.mydubbo.common.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.mydubbo.common.entity.UserCommon;
import com.mydubbo.common.service.UserCommonService;
import org.apache.tomcat.jni.Time;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class UserCommonController {


    @Reference(version = "${demo.service.version}")
    private UserCommonService userCommonService;


    @GetMapping("/user/add")
    public Boolean sayHello() {

        UserCommon userCommon = new UserCommon();
        userCommon.setAge(111);
        userCommon.setName(new Date().toString());
        return userCommonService.addUser(userCommon);
    }

    @GetMapping("/user/list2")
    public List<UserCommon> sel2() {
        return userCommonService.selectUserFromDsGroup();
    }


    @GetMapping("/user/list1")
    public List<UserCommon> sel1() {
        return userCommonService.selectUsersFromDs();
    }
}
