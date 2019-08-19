package com.mydubbo.provider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import service.ApiDemoService;


@Service(version = "${demo.service.version}")
public class ApiDemoServiceImpl implements ApiDemoService {
    @Override
    public String sayHello(String name) {
        return "hello" + name + "I am Provider";
    }
}
