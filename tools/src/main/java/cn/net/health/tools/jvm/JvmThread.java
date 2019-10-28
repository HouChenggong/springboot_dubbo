package cn.net.health.tools.jvm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JvmThread {

    /**
     * https://mp.weixin.qq.com/s/vQxUjl31RSbnSYzCL_Kzvw
     * <p>
     * ：JVM 堆内存溢出后，其他线程是否可继续工作？
     *
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            List<byte[]> list = new ArrayList<byte[]>();
            while (true) {
                System.out.println(new Date().toString() + Thread.currentThread() + "==");
                byte[] b = new byte[1024 * 1024 * 1];
                list.add(b);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 线程二
        new Thread(() -> {
            while (true) {
                System.out.println(new Date().toString() + Thread.currentThread() + "==");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
