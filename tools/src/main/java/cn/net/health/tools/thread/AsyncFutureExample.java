package cn.net.health.tools.thread;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/9 9:38
 */
public class AsyncFutureExample {

    private final static int avaliableProcessors = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor pool = new ThreadPoolExecutor
            (avaliableProcessors, avaliableProcessors * 2,
                    1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5)
                    , new ThreadPoolExecutor.CallerRunsPolicy());

    public static String doSomethingA() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("A.............");
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
        long start = System.currentTimeMillis();
        // 1.创建future任务
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            String result = null;
            try {
                result = doSomethingA();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        });
//        // 2.开启异步单元执行任务A
//        Thread thread = new Thread(futureTask, "threadA");
//        thread.start();

        pool.execute(futureTask);

        // 3.执行任务B
        String taskB = doSomethingB();

        // 4.同步等待线程A运行结束
        String resultA = futureTask.get();

        //5.打印两个任务执行结果
        System.out.println(resultA + "  " + taskB);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(avaliableProcessors);
        delay();
        cycle();
    }
}
