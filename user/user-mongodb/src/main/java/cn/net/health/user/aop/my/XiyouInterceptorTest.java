package cn.net.health.user.aop.my;

import cn.net.health.user.aop.dynamic.DataChangeListener;
import cn.net.health.user.aop.dynamic.DataChangeListerImpl;

/**
 * @author xiyou
 */
public class XiyouInterceptorTest {
    public static void main(String[] args) {
        DataChangeListener dataChangeListener = new DataChangeListerImpl();
        DataChangeListener proxy = (DataChangeListener) XiyouProxyBean.getProxyBean(dataChangeListener, new XiyouInterceptorImpl());
        proxy.sayHello("11111111111111ll");
        proxy.sayHello("xiyou");
        System.out.println("-------------------------------------------------------------------------------");
        proxy.sayHello(null);
    }
}
