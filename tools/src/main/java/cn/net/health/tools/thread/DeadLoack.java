package cn.net.health.tools.thread;

public class DeadLoack {

    private static Object object1 = new Object();
    private static Object object2 = new Object();

    public static void main(String[] args) {
new Thread(new Runnable() {
    @Override
    public void run() {
        synchronized (object1) {
            System.out.println(Thread.currentThread() + "get object1 lock ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread() + "waiting get object1 lock");
            synchronized (object2) {
                System.out.println(Thread.currentThread() + "get object2 lock ");
            }
        }

    }
}, "thread1--").start();
new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println(Thread.currentThread() + "get object2 lock ");
        synchronized (object2) {
            System.out.println("get object2 lock ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "waiting get object1 lock");
            synchronized (object1) {
                System.out.println(Thread.currentThread() + "get object1 lock ");
            }
        }

    }
}, "thread2--").start();


        new Thread(() -> {
            synchronized (object1) {
                System.out.println(Thread.currentThread() + "get resource1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource2");
                synchronized (object2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "线程 1").start();

        new Thread(() -> {
            synchronized (object2) {
                System.out.println(Thread.currentThread() + "get resource2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource1");
                synchronized (object1) {
                    System.out.println(Thread.currentThread() + "get resource1");
                }
            }
        }, "线程 2").start();
    }

}
