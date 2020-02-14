package cn.net.health.tools.hh;

public class DebugJava {
    public static void method2() {
        System.out.println("method2");
        method1();
    }

    public static void method1() {
        System.out.println("method1");
    }


    public static void multiThreadTest() {
        new Thread(() -> {
            System.out.println("1......");
        }, "线程1").start();
        new Thread(() -> {
            System.out.println("2......");
        }, "线程2").start();
        System.out.println("#3");
        System.out.println("4");
    }

    public static void xiu() {
        for (int i = 0; i < 11; i++) {
            if (i == 10) {
                System.out.println("......");
            }
        }
    }

    public static void main(String[] args) {
//        multiThreadTest();
        xiu();
    }


}
