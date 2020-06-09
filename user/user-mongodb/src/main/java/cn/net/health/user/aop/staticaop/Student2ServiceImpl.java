package cn.net.health.user.aop.staticaop;

/**
 * @author xiyou
 */
public class Student2ServiceImpl implements  PersionService {
    @Override
    public void sayHello(String name) {
        System.out.println("student"+name);
    }
}
