package cn.net.health.user.aop.staticaop;

import org.springframework.stereotype.Service;

/**
 * @author xiyou
 */
public class StudentServiceImpl implements  PersionService {
    @Override
    public void sayHello(String name) {
        System.out.println("student"+name);
    }
}
