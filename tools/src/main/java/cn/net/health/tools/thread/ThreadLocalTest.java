package cn.net.health.tools.thread;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/16 10:48
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    /**
     * 在单个线程的线程池中一但抛出了未被捕获的异常时，线程池会回收当前的线程并创建一个新的 Worker；
     * 它也会一直不断的从队列里获取任务来执行，但由于这是一个消费线程，根本没有生产者往里边丢任务，所以它会一直 waiting 在从队列里获取任务处，
     * 所以也就造成了线上的队列没有消费，业务线程池没有执行的问题。
     */

    private final static Logger LOGGER = LoggerFactory.getLogger(ThreadLocalTest.class);


    public static void main(String[] args) throws InterruptedException {

        ExecutorService execute = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        execute.execute(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("=====11=======");
            }
        });

        TimeUnit.SECONDS.sleep(5);


        execute.execute(new Run1());

        //TimeUnit.SECONDS.sleep(5);
        //
        //execute.execute(new Run2());
        //execute.shutdown();

    }


    private static class Run1 implements Runnable {

        @Override
        public void run() {
            int count = 0;
            while (true) {
                count++;
                LOGGER.info("-------222-------------{}", count);

                if (count == 10) {

                    try {
                        System.out.println(1 / 0);
                    } catch (Exception e) {
                        LOGGER.error("Exception", e);
                    }
                }

                if (count == 20) {
                    LOGGER.info("count={}", count);
                    break;
                }
            }
        }
    }

    private static class Run2 implements Runnable {

        public Run2() {
            LOGGER.info("run2 构造函数");
        }

        @Override
        public void run() {
            LOGGER.info("run222222222");
        }
    }
}
