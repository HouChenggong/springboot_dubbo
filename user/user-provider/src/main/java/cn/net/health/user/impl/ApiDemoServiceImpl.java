package cn.net.health.user.impl;


import com.alibaba.dubbo.config.annotation.Service;
import cn.net.health.user.service.ApiDemoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(version = "${demo.service.version}")
public class ApiDemoServiceImpl implements ApiDemoService {
    @Override
    public String sayHello(String name) {
        return "hello" + name + "I am Provider";
    }
}
