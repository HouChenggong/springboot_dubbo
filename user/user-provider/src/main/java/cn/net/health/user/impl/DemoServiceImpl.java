package cn.net.health.user.impl;//package cn.net.health.user.service.impl;
//
//import com.alibaba.dubbo.global.annotation.Service;
//import cn.net.health.user.service.DemoService;
//import org.apache.dubbo.rpc.RpcContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service(version = "1.0.0")
//public class DemoServiceImpl implements DemoService {
//    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);
//
//    @Override
//    public String sayHello(String name) {
//        logger.info("Hello " + name + ", request from common: " + RpcContext.getContext().getRemoteAddress());
//        return "Hello " + name + ", response from common: " + RpcContext.getContext().getLocalAddress();
//    }
//
//}
