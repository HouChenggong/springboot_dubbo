package cn.net.health.tools.thread;


import java.util.concurrent.CountDownLatch;

/**
 * xiyou-todo CountDownLatch的使用
 * 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
 * public void await() throws InterruptedException { };
 * 和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
 * public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };
 * 将count值减1
 * public void countDown() { };
 */
public class TestCountDownLatch {
    final CountDownLatch latch = new CountDownLatch(11);
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getId() + "额外的订单");
            latch.countDown();
            System.out.println("线程" + Thread.currentThread().getId() + "剩余数量" + latch.getCount());

        }
    });

    public void test() {
        thread.start();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    System.out.println("线程" + Thread.currentThread().getId() + "..........start");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getId() + ".........end...");
                    latch.countDown();
                }
            }).start();
        }


        try {

            latch.await();
            System.out.println("调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("10个线程已执行完毕");
    }

    public static void main(String[] args) {
        TestCountDownLatch c = new TestCountDownLatch();
        c.test();
    }
}

