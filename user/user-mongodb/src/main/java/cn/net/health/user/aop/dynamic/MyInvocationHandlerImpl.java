package cn.net.health.user.aop.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 调用处理器实现类
 * 每次生成动态代理类对象时都需要指定一个实现了该接口的调用处理器对象
 *
 * @author xiyou
 */
public class MyInvocationHandlerImpl implements InvocationHandler {

    /**
     * 这个就是我们要代理的真实对象
     */
    private Object subject;

    /**
     * 构造方法，给我们要代理的真实对象赋初值
     *
     * @param subject
     */
    public MyInvocationHandlerImpl(Object subject) {
        this.subject = subject;
    }

    /**
     * 在调用newProxyInstance之前将sun.misc.ProxyGenerator.saveGeneratedFiles系统属性设置为true，
     * 生成的代理类将被自动写入磁盘
     * 获取代理代理对象
     * 主要是获取Proxy0.class文件
     *
     * @return
     */
    public Object getProxy() {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true"); //设置系统属性
        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        return Proxy.newProxyInstance(subject.getClass().getClassLoader(),
                subject.getClass().getInterfaces(), this);
    }


    /**
     * 该方法负责集中处理动态代理类上的所有方法调用。
     * 调用处理器根据这三个参数进行预处理或分派到委托类实例上反射执行
     *
     * @param o       代理类实例
     * @param method  被调用的方法对象
     * @param objects 调用参数
     * @return
     * @throws Throwable
     */

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        //在代理真实对象前我们可以添加一些自己的操作
        System.out.println("在调用之前，我要干点啥呢？");

        System.out.println("Method:" + method);

        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        Object returnValue = method.invoke(subject, objects);

        //在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("在调用之后，我要干点啥呢？");

        return returnValue;

    }
}
