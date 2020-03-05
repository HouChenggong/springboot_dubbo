package cn.net.health.tools.design.single.threadlocal;

/**
 * @author xiyou
 *
 * 测试Threadlocal是否线程安全的类
 */
public class ThreadExecutorTest implements Runnable{
    @Override
    public void run() {
        ThreadLocalSingleton instance = ThreadLocalSingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + " : " + instance);
    }
}
