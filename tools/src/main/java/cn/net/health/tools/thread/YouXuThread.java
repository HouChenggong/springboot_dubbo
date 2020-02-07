package cn.net.health.tools.thread;

import java.util.concurrent.TimeUnit;

/**
 * xiyou-todo 顺序打印10个有序线程
 */
public class YouXuThread {

    public static class YouXuRun implements Runnable {
        private Thread oneThread;

        public YouXuRun(Thread thread) {
            this.oneThread = thread;
        }
        @Override
        public void run() {
            try {
                oneThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread father = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread one = new Thread(new YouXuRun(father), "线程" + i);
            one.start();
            father = one;
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName()
                + " 主线程开始." + "........");
    }
}
