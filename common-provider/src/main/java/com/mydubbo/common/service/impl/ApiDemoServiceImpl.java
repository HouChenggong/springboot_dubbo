package com.mydubbo.common.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.mydubbo.common.service.ApiDemoService;


/**
 * @author xiyou
 */
@Service(version = "${demo.service.version}")
public class ApiDemoServiceImpl implements ApiDemoService {
    @Override
    public String sayHello(String name) {
        return "hello" + name + "I am Provider";
    }
}
