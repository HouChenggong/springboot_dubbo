package cn.net.health.tools.thread;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/9 11:08
 */

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池通过submit方式提交任务，会把Runnable封装成FutureTask。
 * 直接导致了Runnable重写的toString方法在afterExecute统计的时候没有起到我们想要的作用，
 * 最终导致几乎每一个任务（除非hashCode相同）就按照一类任务进行统计。所以这个metricsMap会越来越大，调用metrics接口的时候，会把该map转成一个字符返回
 */
public class GCTest {
    /**
     * 统计各类任务已经执行的数量
     * 此处为了简化代码，只用map来代替metrics统计
     */
    private static Map<String, AtomicInteger> metricsMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>()) {
            /**
             * 统计各类任务执行的数量
             * @param r
             * @param t
             */
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                metricsMap.compute(r.toString(), (s, atomicInteger) ->
                        new AtomicInteger(atomicInteger == null ? 0 : atomicInteger.incrementAndGet()));
            }
        };
        /**
         * 源源不断的任务添加进线程池被执行
         */
        for (int i = 0; i < 1000; i++) {
            threadPoolExecutor.execute(new SimpleRunnable());
        }
        Thread.sleep(1000 * 2);
        System.out.println(metricsMap);
        threadPoolExecutor.shutdownNow();
    }

    static class SimpleRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("SimpleRunnable execute success");
        }

        /**
         * 重写toString用于统计任务数
         *
         * @return
         */
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
