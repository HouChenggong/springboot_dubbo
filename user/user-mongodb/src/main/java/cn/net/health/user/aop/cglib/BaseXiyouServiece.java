package cn.net.health.user.aop.cglib;

/**
 * @author xiyou
 * cglib 要被代理的对象
 * 同时cglib不需要有接口
 */
public class BaseXiyouServiece {

    public void sayName(String name) {
        System.out.println("cglib目标类的方法" + name);
    }
}
