### 1. ThreadLocal单例模式

#### 1. 1 ThreadLocal单例模式实现
```java
/**
 * @author xiyou
 * ThreadLocal 实现单例模式
 */
public class ThreadLocalSingleton {
    private static final ThreadLocal<ThreadLocalSingleton> threadLocalInstanceThreadLocal
            = new ThreadLocal<ThreadLocalSingleton>() {
        @Override
        protected ThreadLocalSingleton initialValue() {
            return new ThreadLocalSingleton();
        }
    };

    private ThreadLocalSingleton() {
    }

    public static ThreadLocalSingleton getInstance() {
        return threadLocalInstanceThreadLocal.get();
    }
}
```

#### 1.2 测试是否是单例模式：

```java

```

```java
public static void main(String[] args) {
    ThreadLocalSingleton instance = ThreadLocalSingleton.getInstance();
    System.out.println(Thread.currentThread().getName() + " : " + instance);
    instance = ThreadLocalSingleton.getInstance();
    System.out.println(Thread.currentThread().getName() + " : " + instance);
    instance = ThreadLocalSingleton.getInstance();
    System.out.println(Thread.currentThread().getName() + " : " + instance);
    instance = ThreadLocalSingleton.getInstance();
    System.out.println(Thread.currentThread().getName() + " : " + instance);
    instance = ThreadLocalSingleton.getInstance();
    System.out.println(Thread.currentThread().getName() + " : " + instance);

    Thread t1 = new Thread(new ThreadExecutorTest());
    Thread t2 = new Thread(new ThreadExecutorTest());

    t1.start();
    t2.start();

    System.out.println("Program End");
}
```
#### 1.3测试结果和分析
结果：

```sql
main : cn.net.health.tools.design.single.threadlocal.ThreadLocalSingleton@783e6358
main : cn.net.health.tools.design.single.threadlocal.ThreadLocalSingleton@783e6358
main : cn.net.health.tools.design.single.threadlocal.ThreadLocalSingleton@783e6358
main : cn.net.health.tools.design.single.threadlocal.ThreadLocalSingleton@783e6358
main : cn.net.health.tools.design.single.threadlocal.ThreadLocalSingleton@783e6358
Program End
Thread-0 : cn.net.health.tools.design.single.threadlocal.ThreadLocalSingleton@47f8df92
Thread-1 : cn.net.health.tools.design.single.threadlocal.ThreadLocalSingleton@7c180569
```



- 不能保证整个程序唯一；
- 可以保证线程唯一；

- 每个线程中拿到的实例都是一个；
- 不同的线程拿到的实例不是一个；

## 2. 主要应用场景

数据源动态路由