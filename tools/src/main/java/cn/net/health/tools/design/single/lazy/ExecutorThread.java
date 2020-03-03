package cn.net.health.tools.design.single.lazy;

/**
 * @author xiyou
 * 测试懒汉模式是否线程安全
 */
public class ExecutorThread implements Runnable {

    @Override
    public void run() {
        SimpleLazyMonitor instance = SimpleLazyMonitor.getMonitor();
        System.out.println(Thread.currentThread().getName() + ":" + instance);
    }
}
