package cn.net.health.user.aop.dynamic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiyou
 * JDK动态代理监听器
 */
@Slf4j
public class DataChangeListerImpl implements DataChangeListener {
    @Override
    public void listener(Object obj) {
        log.info("user update data={}", obj.toString());
    }

    @Override
    public String sayHello(String name) {
        return name + System.currentTimeMillis();
    }

    @Override
    public String sayHello2(String name) {
        return name + System.currentTimeMillis();
    }
}
