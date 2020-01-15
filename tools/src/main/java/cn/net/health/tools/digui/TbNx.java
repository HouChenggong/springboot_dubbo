package cn.net.health.tools.digui;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/15 10:18
 * https://leetcode-cn.com/problems/n-th-tribonacci-number/submissions/
 */
@Slf4j
public class TbNx {
    /**
     * 累加Fib(n+3)=Fib(n)+Fib(n+2)+Fib(n+1)
     * 斐波那契数列
     * 这个不存在深度问题
     *
     * @param i
     * @return
     */
    public static int sanFab2(int i) {
        if (i == 1) {
            return i;
        } else if (i <= 0) {
            return 0;
        } else if (i == 2) {
            return 1;
        } else {

        }
        int a0 = 0;
        int a1 = 1;
        int a2 = 1;
        int result = 0;

        for (int n = 3; n <= i; n++) {
            result = a0 + a1 + a2;
            a0 = a1;
            a1 = a2;
            a2 = result;
        }
        return result;

    }

    /**
     * 当递归的层级越大，时间越长
     * 累加Fib(n+3)=Fib(n)+Fib(n+2)+Fib(n+1)
     * 斐波那契数列
     *
     * @param i
     * @return
     */
    public static int sanFab(int i) {
        int dp[] = new int[i + 1];
        if (dp[i] != 0) {
            return dp[i];
        }
        if (i == 1 || i == 2) {
            return 1;
        } else if (i == 0) {
            return 0;
        } else {

        }
        int res = sanFab(i - 3) + sanFab(i - 2) + sanFab(i - 1);
        dp[i] = res;
        return res;
    }

    /**
     * 累加Fib(n+3)=Fib(n)+Fib(n+2)+Fib(n+1)
     * 斐波那契数列
     *
     * @param i
     * @return
     */
    public static int sanFab3(int i) {
        if (i == 1 || i == 2) {
            return 1;
        } else if (i == 0) {
            return 0;
        } else {

        }
        return sanFab(i - 3) + sanFab(i - 2) + sanFab(i - 1);

    }

    public static void main(String[] args) {
        System.out.println("力扣1137. 第[{}]个泰波那契数问题,结果是：[{}]" + sanFab2(25));
        System.out.println("力扣1137. 第[{}]个泰波那契数问题,结果是：[{}]" + sanFab(25));
        System.out.println("力扣1137 如果N特别大，时间回超时：[{}]" + sanFab3(25));
    }
}
