package cn.net.health.tools.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/9 17:21
 * 使用Semphore进行并发流控
 * Java 并发库的Semaphore 可以很轻松完成信号量控制，
 * Semaphore可以控制某个资源可被同时访问的个数，通过 acquire() 获取一个许可，如果没有就等待，
 * 而 release() 释放一个许可。单个信号量的Semaphore对象可以实现互斥锁的功能，
 * 并且可以是由一个线程获得了“锁”，再由另一个线程释放“锁”，这可应用于死锁恢复的一些场合。
 * 下面的Demo中申明了一个只有5个许可的Semaphore，而有20个线程要访问这个资源，
 * 通过acquire()和release()获取和释放访问许可：
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        final Semaphore semaphor = new Semaphore(5);
        for (int i = 0; i < 20; i++) {
            final int no = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphor.acquire();
                        System.out.println("Acessing:" + no);
                        Thread.sleep(1000);
                        semaphor.release();
                        System.out.println("**" + semaphor.availablePermits());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            executor.execute(runnable);
        }
        executor.shutdown();
    }

}
