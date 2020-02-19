package cn.net.health.tools.thread;

/**
 * 用return和interrupt方法结合停止线程
 * https://mp.weixin.qq.com/s/bkIoi4Hro2HrH_dr7WHHIA
 */
public class StopThread extends Thread {
    public void run() {
        while (true) {
            if (this.isInterrupted()) {
                System.out.println("线程已经被停止了");
                return;
            }
            System.out.println(System.currentTimeMillis());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new StopThread();
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }
}
