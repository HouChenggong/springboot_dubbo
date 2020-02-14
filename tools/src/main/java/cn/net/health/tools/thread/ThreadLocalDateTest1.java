package cn.net.health.tools.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * https://mp.weixin.qq.com/s/L7wbJCgfvdjPdKPWsB0nZg
 * todo-xiyou 测试thread Local
 */
public class ThreadLocalDateTest1 {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);


    public static void main(String[] args) throws InterruptedException {
//        method1();
//        method2();
        method3();

    }

    public static void method3() {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            //提交任务
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalDateTest1().date(finalI);
                    System.out.println(Thread.currentThread().getName() + "——" + date);
                }
            });
        }
        threadPool.shutdown();
    }

    public static void method2() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalDateTest1().date(finalI);
                    System.out.println(Thread.currentThread().getName() + "——" + date);
                }
            }, "线程:" + i).start();
            //线程启动后，休眠100ms
            Thread.sleep(100);
        }
    }


    public static void method1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalDateTest1().date(10);
                System.out.println(Thread.currentThread().getName() + "——" + date);
            }
        }, "thread-2").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalDateTest1().date(104707);
                System.out.println(Thread.currentThread().getName() + "——" + date);
            }
        }, "thread-1").start();
    }

    public String date(int seconds) {

        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT 开始计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
