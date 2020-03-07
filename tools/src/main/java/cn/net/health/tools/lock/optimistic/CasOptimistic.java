package cn.net.health.tools.lock.optimistic;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS 实现的乐观锁
 *
 * @author xiyou
 */
public class CasOptimistic {
    /**
     * 查看普通数组的构成
     */
    public static void getSimpleArr() {
        int arr[] = new int[]{};
        String arrStr = ClassLayout.parseInstance(arr).toPrintable();
        System.out.println(arrStr);
        System.out.println("----------------------");
    }

    /**
     * 查看普通对象的构成
     */
    public static void getSimple() {
        Object o = new Object();
        String str = ClassLayout.parseInstance(o).toPrintable();
        System.out.println(str);
        System.out.println("----------------------");
    }


    /**
     * 查看普通对象加上锁之后的构成，发现只有头文件变了，说明sync只与对象头文件相关
     */
    public static void getSyncSimple() {
        Object o = new Object();
        synchronized (o) {
            String str = ClassLayout.parseInstance(o).toPrintable();
            System.out.println(str);
        }
        System.out.println("----------------------");
    }

    /**
     * 锁消除 lock eliminate
     *
     * @param str1
     * @param str2
     */
    public void add(String str1, String str2) {
        StringBuffer sb = new StringBuffer();
        sb.append(str1).append(str2);
    }

    /**
     * 锁粗化 lock coarsening
     *
     * @param str
     * @return
     */
    public String test(String str) {
        int i = 0;
        StringBuffer sb = new StringBuffer();
        while (i < 100) {
            sb.append(str);
            i++;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        getSimpleArr();
        getSimple();
        getSyncSimple();
    }
}
