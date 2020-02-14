package cn.net.health.tools.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDateTest2 {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    //只创建一次 SimpleDateFormat 对象，避免不必要的资源消耗
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void main(String[] args) throws InterruptedException {
//        method1();
//        method2();
        method3();
    }

    /**
     * 非线程安全打印
     */
    private static void method1() {
        Set<String> stringSet  =new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            //提交任务
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalDateTest2().date(finalI);
                    System.out.println(Thread.currentThread().getName() + "——" + date);
                }
            });
        }
        threadPool.shutdown();
    }

    /**
     * sync线程安全打印
     * 但是也可以用threadLocal，简单些，参见方法3
     */
    private static void method2() {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            //提交任务
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalDateTest2().dateSync(finalI);
                    System.out.println(Thread.currentThread().getName() + "——" + date);
                }
            });
        }
        threadPool.shutdown();
    }


    /**
     * 用threadLocal解决线程安全问题
     */
    public static void method3() {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            //提交任务
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalDateTest2().date3(finalI);
                    System.out.println(date);
                }
            });
        }
        threadPool.shutdown();
    }


    public String date3(int seconds) {

        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT 开始计时
        Date date = new Date(1000 * seconds);
        //获取 SimpleDateFormat 对象
        SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
        return dateFormat.format(date);
    }


    /**
     * 非线程安全，因为多个线程同时访问dateFormat对象,但是SimpleDateFormat是非线程安全的。
     * 运行结果
     * 出现了秒数相同的打印结果，这显然是不正确的。
     *
     * @param seconds
     * @return
     */
    public String date(int seconds) {

        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT 开始计时
        Date date = new Date(1000 * seconds);
        return dateFormat.format(date);
    }

    public String dateSync(int seconds) {

        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT 开始计时
        Date date = new Date(1000 * seconds);
        String s;
        synchronized (ThreadLocalDateTest2.class) {
            s = dateFormat.format(date);
        }
        return s;
    }

}

class ThreadSafeFormatter {

    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new
            ThreadLocal<SimpleDateFormat>() {

                //创建一份 SimpleDateFormat 对象
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                }
            };
}
