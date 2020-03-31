package cn.net.health.user.aop.my;

import java.lang.reflect.InvocationTargetException;

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
     * 取代原有事件的方法
     *
     * @param invocation 可以通过他的proceed方法，回调原有事件
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    Object around(XiyouInvocation invocation) throws InvocationTargetException, IllegalAccessException;


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
