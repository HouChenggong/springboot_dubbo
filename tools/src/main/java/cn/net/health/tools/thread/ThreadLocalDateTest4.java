package cn.net.health.tools.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDateTest4 {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

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
                    String date = new ThreadLocalDateTest4().printDate(finalI);
                    System.out.println(date);
                }
            });
        }
        threadPool.shutdown();
    }

    public String printDate(int seconds) {

        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT 开始计时
        Date date = new Date(1000 * seconds);
        //获取 SimpleDateFormat 对象
        SimpleDateFormat dateFormat = ThreadSafeFormatter4.dateFormatThreadLocal.get();
        return dateFormat.format(date);
    }


    public static void main(String[] args) {
        Thread thread;
        ThreadLocalDateTest4.method3();
    }




}
class ThreadSafeFormatter4 {

    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new
            ThreadLocal<SimpleDateFormat>() {

                //创建一份 SimpleDateFormat 对象
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                }
            };
}


