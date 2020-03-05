package cn.net.health.tools.design.single.threadlocal;

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
}
