package cn.net.health.tools.thread;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class MyAutomicInteger {

    private volatile int value;

    private static long offset;

    private static Unsafe unsafe;


    static {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            unsafe = (Unsafe) theUnsafeField.get(null);
            Field field = MyAutomicInteger.class.getDeclaredField("value");
            offset = unsafe.objectFieldOffset(field);//获得偏移地址
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void increment(int num) {
        int tempValue;
        do {
            tempValue = unsafe.getIntVolatile(this, offset);//拿到值
        } while (!unsafe.compareAndSwapInt(this, offset, tempValue, value + num));//CAS自旋
    }

    public int get() {
        return value;
    }


    public static void main(String[] args) {
        Thread[] threads = new Thread[20];
        MyAutomicInteger atomicInteger = new MyAutomicInteger();
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicInteger.increment(1);
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("x=" + atomicInteger.get());
    }
}


