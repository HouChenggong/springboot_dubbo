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
        DataChangeListener realSubject = new DataChangeListerImpl();


        /**
         * InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
         * 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用.
         * 即：要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法
         */
        InvocationHandler handler = new MyInvocationHandlerImpl(realSubject);
        MyInvocationHandlerImpl handler1=new MyInvocationHandlerImpl(realSubject);
        handler1.getProxy();
        DataChangeListener2 realSubject2 = new DataChangeListerImpl2();

        MyInvocationHandlerImpl handler2=new MyInvocationHandlerImpl(realSubject2);
        handler2.getProxy();

        ClassLoader loader = realSubject.getClass().getClassLoader();
        Class[] interfaces = realSubject.getClass().getInterfaces();
        /**
         * 该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
         */
        DataChangeListener subject = (DataChangeListener) Proxy.newProxyInstance(loader, interfaces, handler);

        System.out.println("动态代理对象的类型：" + subject.getClass().getName());

        String hello = subject.sayHello("xiyou");
        System.out.println(hello);
        MongoUser user = new MongoUser();
        user.setUserId(System.currentTimeMillis() + "");
        user.setUserName("xiyou");
        subject.listener(user);

    }


}
