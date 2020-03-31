## 手撸AOP

### 1. 随便写一个接口和实现类

- 接口

```
/**
 * @author xiyou
 * JDK动态代理
 */
public interface DataChangeListener {

    /**
     * 测试JDK动态代理
     *
     * @param name
     * @return
     */
    String sayHello(String name);

}
```

- 实现类

```
/**
 * @author xiyou
 * JDK动态代理监听器
 */
public class DataChangeListerImpl implements DataChangeListener {

    @Override
    public String sayHello(String name) {
        if (name == null) {
            throw new RuntimeException("param is null");
        }
        return name + System.currentTimeMillis();
    }
    }
```

### 2. 开发自己的拦截器和实现类

- 拦截器

```java
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
```

- 里面用到的对象

```java
/**
 * @author xiyou
 * 其实就是一个对象
 */
public class XiyouInvocation {


    private Object[] params;

    private Method method;

    private Object target;

    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target, params);
    }


    public XiyouInvocation(Object[] params, Method method, Object target) {
        this.params = params;
        this.method = method;
        this.target = target;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
```

- 拦截器实现类

```java

import java.lang.reflect.InvocationTargetException;

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
    public Object around(XiyouInvocation invocation) throws InvocationTargetException, IllegalAccessException {
        System.out.println("around before ..........");
        Object o = invocation.proceed();
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

```

### 3. 实现JDK动态代理（核心）

注意里面的参数一个不能错，错了就死循环了，建议直接拷贝，我这里就错了很多次

```java

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

```

### 4. 测试和结果

```java
/**
 * @author xiyou
 */
public class XiyouInterceptorTest {
    public static void main(String[] args) {
        DataChangeListener dataChangeListener = new DataChangeListerImpl();
        DataChangeListener proxy = (DataChangeListener) XiyouProxyBean.getProxyBean(dataChangeListener, new XiyouInterceptorImpl());
        proxy.sayHello("11111111111111ll");
        proxy.sayHello("xiyou");
        System.out.println("----------------------------------------------");
        proxy.sayHello(null);
    }
}
```

结果如下：

```sql
before ..........
around before ..........
around after ..........
after ..........
afterRetruning ..........
before ..........
around before ..........
around after ..........
after ..........
afterRetruning ..........
---------------------------------------------------------- 
before ..........
around before ..........
after ..........
afterThrowing ..........
```

