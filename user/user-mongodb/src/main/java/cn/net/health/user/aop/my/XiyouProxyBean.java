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
    private Object myObject = null;


    private XiyouInterceptor xiyouInterceptor = null;


    /**
     * 绑定代理对象
     *
     * @param target      被代理对象
     * @param interceptor 拦截器
     * @return
     */
    public static Object getProxyBean(Object target, XiyouInterceptor interceptor) {
        XiyouProxyBean proxyBean = new XiyouProxyBean();
        //保存代理对象
        proxyBean.myObject = target;
        proxyBean.xiyouInterceptor = interceptor;

        //生成代理对象
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), proxyBean);
        return proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean excetionFlag = false;
        XiyouInvocation invocation = new XiyouInvocation(args, method, myObject);
        Object returnObj = null;
        try {
            if (this.xiyouInterceptor.before()) {
                returnObj = this.xiyouInterceptor.around(invocation);
            } else {
                returnObj = method.invoke(myObject, args);
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
