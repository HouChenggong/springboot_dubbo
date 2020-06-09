package cn.net.health.user.aop.my;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xiyou
 * 真正AOP的核心
 */
public class XiyouProxyBean implements InvocationHandler {

    /**
     * 这个就是我们要代理的真实对象
     */
    private Object target;

    private XiyouInterceptor xiyouInterceptor;

    public XiyouProxyBean(Object myObject, XiyouInterceptor xiyouInterceptor) {
        this.target = myObject;
        this.xiyouInterceptor = xiyouInterceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean excetionFlag = false;
        Object returnObj = null;
        try {
            if (this.xiyouInterceptor.before()) {
                returnObj = this.xiyouInterceptor.around(args,method,target);
            } else {
                returnObj = method.invoke(target, args);
            }
        } catch (Exception e) {
            //产生异常
            excetionFlag = true;
        }
        this.xiyouInterceptor.after();
        if (excetionFlag) {
            this.xiyouInterceptor.afterThrowing();
        } else {
            this.xiyouInterceptor.afterRetruning();
            return returnObj;
        }
        return null;
    }
}
