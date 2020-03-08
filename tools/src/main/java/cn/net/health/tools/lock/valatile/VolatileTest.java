package cn.net.health.tools.lock.valatile;

import java.util.*;

/**
 * @author xiyou
 */
public class VolatileTest {

    public static volatile Boolean falg = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (falg) {
//               doSomething
            }
            System.out.println("end");
        }, "server").start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        falg = false;
    }

    public static String generateTheString(int n) {
        if (n > 500 || n < 1) {
            return null;
        }
        String[] arr = new String[]{"a", "b"};
        StringBuffer result = new StringBuffer();
        if (n % 2 == 0) {
            n = n - 1;
            result.append(arr[1]);
        }
        for (int i = 0; i < n; i++) {
            result.append(arr[0]);
        }
        return result.toString();

    }

    public static int numTimesAllBlue(int[] light) {
        if (light.length == 0) {
            return 0;
        }
        int size = 0;
        int total = 1;
        int arrTotal = 0;
        for (int i = 0; i < light.length; i++) {
            arrTotal += light[i];
            if (arrTotal == total) {
                size++;
            }
            total += i + 2;
        }
        return size;
    }

    public static int count = 0;

    public static void numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        count += informTime[headID];
        for (int i = 0, len = manager.length; i < len; i++) {
            if (manager[i] == headID) {
                numOfMinutes(n, i, manager, informTime);
            }
        }
    }
//
//    public static void main(String[] args) {
//         numOfMinutes(15,
//                0,
//                new int[]{-1, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6}
//                , new int[]{1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}
//        );
//        System.out.println(count);
//    }


}
