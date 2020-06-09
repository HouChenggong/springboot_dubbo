package cn.net.health.tools.thread;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/9 9:38
 */
public class AsyncFutureExample {

//    private final static int avaliableProcessors = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor pool = new ThreadPoolExecutor
            (2, 3,
                    1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10)
                    , new ThreadPoolExecutor.CallerRunsPolicy());

    public static String doSomethingA() {
        System.out.println("Astart.............");
        try {
            Thread.sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("A.end............");
        return "TaskA....Result";
    }

    public static String doSomethingB() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("B.............");
        return "TaskB....Result";
    }

    private static void delay() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(() -> System.out.println(Thread.currentThread().getName() + " 延迟 3 秒"), 3,
                TimeUnit.SECONDS);
    }

    private static void cycle() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(
                () -> System.out.println(Thread.currentThread().getName() + " 延迟 1 秒，每 3 秒执行一次"), 1, 3,
                TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws Exception {
        for(int i=0;i<10;i++){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    doSomethingA();
                }
            });
        }
    }
}
