package cn.net.health.user.aop.my;

import cn.net.health.user.aop.dynamic.DataChangeListener;
import cn.net.health.user.aop.dynamic.DataChangeListerImpl;

import java.lang.reflect.Proxy;

/**
 * @author xiyou
 */
public class XiyouInterceptorTest {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true"); //设置系统属性
        DataChangeListener target = new DataChangeListerImpl();
        XiyouProxyBean proxyBean=new XiyouProxyBean(target,new XiyouInterceptorImpl());
        DataChangeListener proxy =(DataChangeListener) Proxy.newProxyInstance(
                target.getClass().getClassLoader()
                , target.getClass().getInterfaces()
                , proxyBean);
        proxy.sayHello("11111111111111ll");

    }
}
