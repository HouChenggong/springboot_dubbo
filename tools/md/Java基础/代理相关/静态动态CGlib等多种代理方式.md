##  静态代理

若代理类在程序运行前就已经存在，这种方式称为静态代理 

静态：由程序员创建代理类或特定工具自动生成源代码再对其编译。在程序运行前代理类的.class文件就已经存在了。

顶层接口A

```java
/**
 * @author xiyou
 * JDK动态代理
 */
public interface DataChangeListener {
 public void sayHello(String name)；
}
```

二层接口A2实现了A的方法

```java
/**
 * @author xiyou
 */
public class StudentServiceImpl implements  PersionService {
    @Override
    public void sayHello(String name) {
        System.out.println("student"+name);
    }
}
```

代理接口P2也实现了A的方法，只不过P2里面要传入A

```java
/**
 * @author xiyou
 * 静态代理对象
 */
public class ProxyStudentServiceImpl implements PersionService {

    private PersionService persionService;

    public ProxyStudentServiceImpl(PersionService sa) {
        this.persionService = sa;
    }

    @Override
    public void sayHello(String name) {
        System.out.println("proxy start" + name);
        persionService.sayHello(name);
        System.out.println("proxy end" + name);

    }

    public static void main(String[] args) {

        ProxyStudentServiceImpl proxy = new ProxyStudentServiceImpl(new StudentServiceImpl());
        proxy.sayHello("xiyou");
    }
}
```

- 结果

```sql
proxy startxiyou
studentxiyou
proxy endxiyou
```

- 分析：上述代码只为PersionService实现了代理，如果想要为其它service引入代理，就需要用动态代理

## JDK动态代理

### JDK动态代理Demo



因为Spring的事务控制依赖于AOP，AOP底层实现便是动态代理，环环相扣

- 写一个要代理的接口

```java
/**
 * @author xiyou
 * JDK动态代理
 */
public interface DataChangeListener {
    /**
     * When db changed, we will callback this method to execute custom business.
     * Warning: be careful blocking thread.
     *
     * @param obj Model of db declare
     */
    void listener(Object obj);

    /**
     * 测试JDK动态代理
     *
     * @param name
     * @return
     */
    String sayHello(String name);

}
```

- 接口实现写一下

```java
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiyou
 * JDK动态代理监听器
 */
@Slf4j
public class DataChangeListerImpl implements DataChangeListener {
 
    @Override
    public String sayHello(String name) {
        return name + System.currentTimeMillis();
    }
}
```

- 真正的代理实现

1. 调用处理器实现类
2. 每次生成动态代理类对象时都需要指定一个实现了该接口的调用处理器对象





```java

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * 调用处理器实现类
 * 每次生成动态代理类对象时都需要指定一个实现了该接口的调用处理器对象
 *
 * @author xiyou
 */
public class InvocationHandlerImpl implements InvocationHandler {

    /**
     * 这个就是我们要代理的真实对象
     */
    private Object subject;

    /**
     * 构造方法，给我们要代理的真实对象赋初值
     *
     * @param subject
     */
    public InvocationHandlerImpl(Object subject) {
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
```

- 动态代理演示

```java

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
        InvocationHandler handler = new InvocationHandlerImpl(realSubject);

        ClassLoader loader = realSubject.getClass().getClassLoader();
        Class[] interfaces = realSubject.getClass().getInterfaces();
        /**
         * 该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
         */
        DataChangeListener subject = (DataChangeListener) Proxy.newProxyInstance(loader, interfaces, handler);

        System.out.println("动态代理对象的类型：" + subject.getClass().getName());

        String hello = subject.sayHello("jiankunking");
        System.out.println(hello);
        MongoUser user = new MongoUser();
        user.setUserId(System.currentTimeMillis()+"");
        user.setUserName("xiyou");
        subject.listener(user);
    }

}
```

- 打印结果

```java
动态代理对象的类型：com.sun.proxy.$Proxy0
在调用之前，我要干点啥呢？
Method:public abstract java.lang.String cn.net.health.user.aop.dynamic.DataChangeListener.sayHello(java.lang.String)
在调用之后，我要干点啥呢？
jiankunking1585573526106
在调用之前，我要干点啥呢？
Method:public abstract void cn.net.health.user.aop.dynamic.DataChangeListener.listener(java.lang.Object)
2020-03-30 21:05:26.108--- [main] INFO  cn.net.health.user.aop.dynamic.DataChangeListerImpl ---- user update data=MongoUser(userId=1585573526107, roleId=null, nickName=null, userName=xiyou, passWord=null, salt=null, state=null, updateTime=null, createTime=null, phone=null, mail=null, qq=null, imgUrl=null)
在调用之后，我要干点啥呢？
```



Java 动态代理，具体有如下四步骤：

- 通过实现 InvocationHandler 接口创建自己的调用处理器；
  - 主要是实现里面的Invoke方法
- 通过为 Proxy 类指定 ClassLoader 对象和一组 interface 来创建动态代理类；
  - 步骤就是：写一个接口A和A的实现类AImpl  然后获取它的ClassLoader 和Interface
- 通过反射机制获得动态代理类的构造函数，其唯一参数类型是调用处理器接口类型；
  - 因为有类并不一定有实例，所以要反射获取构造函数
- 通过构造函数创建动态代理类实例，构造时调用处理器对象作为参数被传入。
  - 有了构造函数就能创建实例

### 获取proxy0.class

在idea中双击shift，查询Proxy0.class文件即可，加入你的Interface里面有3个方法，那么Proxy0.class里面就有6个方法，分别是3+hash+equals()+toString()=6个方法

### jdk动态代理 总结和应用



就写到这里吧，做个总结，`java.lang.reflect.Proxy` 是JDK提供的动态代理类，我们探索过程中，至少发现了2处使用反射的地方，一处是 反射构造函数，然后执行它，创建`代理类对象`的过程，一处是 使用代理类对象，反射创建`代理对象`的过程（有没有晕？代理类对象和代理对象是不一样的！）, 使用了反射，效率自然比不上静态代理，但是它能保证 真实对象无限扩展的时候，代理类不用做修改，只需要在创建代理类的时候，传入不同的真实对象即可，这种做法才符合程序设计的开闭原则，对扩展开放，对修改关闭。

- 使用代理类对象，反射创建`代理对象`的过程如下：

我们去使用一个类，一定会先和获得这个类的class对象，利用Proxy可以动态去创建Proxy0，proxy1，这样子的class对象。然后才利用这个对象反射执行构造函数去创建 proxy0对象。





一个接口只会生成一个Proxy0.class,如果是N个，则会生成Proxy1.class  Proxy2.class.... ProxyN-1.class

当我们具象的查看某一个动态代理class反编译文件时，比如，它内部就是采用静态代理的方式进行包装。其动态是体现在，能够在给定的接口和情况下，动态生成代理类，如Proxy0，，Proxy2等等，不必手动创建，使用起来更灵活。

#### 应用

- aop
- 拦截器
- 日志拦截
- 权限拦截
- 事务





下面我们看一下具体的proxy0.class

```java

public final class $Proxy0 extends Proxy implements DataChangeListener {
    private static Method m1;
    private static Method m3;
    private static Method m4;
    private static Method m2;
    private static Method m5;
    private static Method m0;

    public $Proxy0(InvocationHandler var1) throws  {
        super(var1);
    }

    public final boolean equals(Object var1) throws  {
        try {
            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final String sayHello2(String var1) throws  {
        try {
            return (String)super.h.invoke(this, m3, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final String sayHello(String var1) throws  {
        try {
            return (String)super.h.invoke(this, m4, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final String toString() throws  {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void listener(Object var1) throws  {
        try {
            super.h.invoke(this, m5, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final int hashCode() throws  {
        try {
            return (Integer)super.h.invoke(this, m0, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m3 = Class.forName("cn.net.health.user.aop.dynamic.DataChangeListener").getMethod("sayHello2", Class.forName("java.lang.String"));
            m4 = Class.forName("cn.net.health.user.aop.dynamic.DataChangeListener").getMethod("sayHello", Class.forName("java.lang.String"));
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m5 = Class.forName("cn.net.health.user.aop.dynamic.DataChangeListener").getMethod("listener", Class.forName("java.lang.Object"));
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}
```

## cglib动态代理实现AOP拦截

- 写一个类，没有必要必须是接口

```java
/**
 * @author xiyou
 * cglib 要被代理的对象
 * 同时cglib不需要有接口
 */
public class BaseXiyouServiece {

    public void sayName(String name) {
        System.out.println("cglib目标类的方法" + name);
    }
}
```

- 测试

```java

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.invoke.MethodHandleInfo;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author xiyou
 * cglib动态的拦截切入成功了
 * cglib动态代理的方式是在运行时动态的生成目标类（Base）的子类,并且在目标类现有方法的基础上添加了很多cglib特有的方法。
 */
public class CglibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before-------切面加入逻辑");
        methodProxy.invokeSuper(o, objects);
        System.out.println("after-------切面加入逻辑");
        return null;
    }

    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(BaseXiyouServiece.class);
        //回调方法的参数为代理类对象CglibProxy,最后增强目标类调用的是代理类对象CglibProxy中的intercept方法
        enhancer.setCallback(proxy);
        //此刻，base不是单车的目标类，而是增强过的目标类
        BaseXiyouServiece base = (BaseXiyouServiece) enhancer.create();
        base.sayName("xiyou");



        //没有被增强过的base类
        BaseXiyouServiece base2 = new BaseXiyouServiece();
        System.out.println("未增强过的类的父类：" + base2.getClass().getSuperclass().getName());
        System.out.println("=============打印增未强过的目标类的方法===============");
        printMethods(base2.getClass());//打印没有增强过的类的所有方法


        Class<? extends BaseXiyouServiece> baseClass = base.getClass();
        //查看增强过的类的父类是不是未增强的Base类
        System.out.println("增强过的类的父类：" + baseClass.getSuperclass().getName());
        System.out.println("============打印增强过的类的所有方法==============");
        printMethods(baseClass);
    }

    //打印该类的所有方法
    public static void printMethods(Class cl) {
        System.out.println();
        //获得包含该类所有其他方法的数组
        Method[] methods = cl.getDeclaredMethods();
        //遍历数组
        for (Method method : methods) {
            System.out.print("  ");
            //获得该方法的修饰符并打印
            String modifiers = Modifier.toString(method.getModifiers());
            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }
            //打印方法名
            System.out.print(method.getName() + "(");

            //获得该方法包含所有参数类型的Class对象的数组
            Class[] paramTypes = method.getParameterTypes();
            //遍历数组
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) {
                    System.out.print(",");
                }
                System.out.print(paramTypes[i].getName());
            }
            System.out.println(");");
        }
    }
}
```



## cglib和JDK动态代理的区别



java动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。

而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。

1、如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP 
2、如果目标对象实现了接口，可以强制使用CGLIB实现AOP 

3、如果目标对象没有实现了接口，必须采用CGLIB库，spring会自动在JDK动态代理和CGLIB之间转换



​    1. JDK动态代理只能对实现了接口的类生成代理，而不能针对类 

​    2. CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法因为是继承，所以该类或方法最好不要声明成final