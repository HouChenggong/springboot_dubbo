package cn.net.health.user.aop.dynamic;


import cn.net.health.user.entity.MongoUser;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 动态代理演示
 */
public class DynamicProxyDemonTest {
    public static void main(String[] args) {
        //代理的真实对象
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true"); //设置系统属性
        DataChangeListener realSubject = new DataChangeListerImpl();
        MyInvocationHandlerImpl myInvocationHandler=new MyInvocationHandlerImpl(realSubject);
        DataChangeListener subject = (DataChangeListener) Proxy.newProxyInstance(
                realSubject.getClass().getClassLoader(),
                realSubject.getClass().getInterfaces(),
                myInvocationHandler);

        String hello = subject.sayHello("xiyou");
        subject.listener(new Object());

    }

}
