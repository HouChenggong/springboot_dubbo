package cn.net.health.user.aop.my;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xiyou
 * 自己的拦截器接口
 */
public interface XiyouInterceptor {

    /**
     * 事前方法
     *
     * @return
     */
    boolean before();


    /**
     * 事后方法
     */
    void after();

    /**
     * 要环绕的方法
     * @param params
     * @param method
     * @param target
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object around(Object[] params, Method method, Object target) throws InvocationTargetException, IllegalAccessException;


    /**
     * 事件无异常的时候执行
     */
    void afterRetruning();

    /**
     * 事件异常的时候执行
     */
    void afterThrowing();

    /**
     * 是否使用around取代原来的方法
     *
     * @return
     */
    boolean userAround();

}
