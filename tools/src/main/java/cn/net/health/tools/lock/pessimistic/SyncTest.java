package cn.net.health.tools.lock.pessimistic;


//测试类

/**
 * @author xiyou
 */
public class SyncTest extends Thread {

    @Override
    public void run() {
        Sync sync = new Sync();
        sync.syncClass();
        sync.simple();
        sync.syncSimple();
        sync.syncSimpleThis();

    }


    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            Thread thread = new SyncTest();
            thread.setName("线程" + i);
            thread.start();
        }
    }
}


class Sync {
    /**
     * 静态锁在当前方法上
     */
    public static synchronized void staticSync() {

        System.out.println(Thread.currentThread().getName() + "静态锁staitcMethodA开始..");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "静态锁staitcMethodA结束..");

    }

    /**
     * 锁住当前的class
     */
    public void syncClass() {
        synchronized (Sync.class) {
            System.out.println(Thread.currentThread().getName() + "锁住当前的classA开始..");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "锁住当前的classA结束..");
        }
    }

    public void simple() {
        System.out.println(Thread.currentThread().getName() + "---------   一般的方法");
    }


    /**
     * 普通方式上加sync
     */
    public synchronized void syncSimple() {
        System.out.println(Thread.currentThread().getName() + "========普通方式上加syncB开始..");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "=========普通方式上加syncB结束..");
    }

    /**
     *syncThis
     */
    public void syncSimpleThis() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + "========普通锁B  syncThis开始..");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "=========普通锁B syncThis结束..");
        }

    }

}
