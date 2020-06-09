package cn.net.health.user.aop.my;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xiyou
 * 自己的拦截器实现
 */
public class XiyouInterceptorImpl implements XiyouInterceptor {
    @Override
    public boolean before() {
        System.out.println("before ..........");
        return true;
    }

    @Override
    public void after() {
        System.out.println("after ..........");
    }

    @Override
    public Object around(Object[] params, Method method, Object target) throws InvocationTargetException, IllegalAccessException {
        System.out.println("around before ..........");
        Object o = method.invoke(target, params);
        System.out.println("around after ..........");
        return o;
    }

    @Override
    public void afterRetruning() {
        System.out.println("afterRetruning ..........");
    }

    @Override
    public void afterThrowing() {
        System.out.println("afterThrowing ..........");
    }

    @Override
    public boolean userAround() {
        return true;
    }
}
