package cn.net.health.tools.lock.valatile;

import java.util.*;

/**
 * @author xiyou
 */
public class VolatileTest {

    public static volatile Boolean falg = true;

//    public static void main(String[] args) {
//        new Thread(() -> {
//            while (falg) {
////               doSomething
//            }
//            System.out.println("end");
//        }, "server").start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        falg = false;
//    }

    /**
     * https://leetcode-cn.com/problems/generate-a-string-with-characters-that-have-odd-counts/
     * 力扣5352
     *
     * @param n
     * @return
     */
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

    /**
     * https://leetcode-cn.com/contest/weekly-contest-179/problems/bulb-switcher-iii/
     * 力扣5353
     *
     * @param light
     * @return
     */
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


    /**
     * https://leetcode-cn.com/problems/time-needed-to-inform-all-employees/
     * 力扣5354
     * 可以理解为一个多叉树，求最大路径
     *
     * @param n
     * @param headID
     * @param manager
     * @param informTime
     * @return
     */
    public static int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        int total = 0;
        for (int i = 0, len = informTime.length; i < len; i++) {
            //剪枝，只要最底层的
            if (informTime[i] == 0) {
                int temp = 0;
                int index = i;
                while (index != -1) {
                    temp += informTime[index];
                    index = manager[index];
                }
                total = Math.max(temp, total);
            }
        }
        return total;
    }

 

    public static void main(String[] args) {
        int total = numOfMinutes(15,
                0,
                new int[]{-1, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6}
                , new int[]{1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}
        );
        System.out.println(total);
    }


}
