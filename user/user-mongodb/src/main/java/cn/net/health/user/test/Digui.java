package cn.net.health.user.test;


import lombok.extern.slf4j.Slf4j;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/13 11:51
 *
 */
@Slf4j
public class Digui {
    /**
     * 累加
     *
     * @param i
     * @return
     */
    public static int sum(int i) {
        if (i == 1) {
            return 1;
        }
        return i + sum(i - 1);

    }

    /**
     * 累乘
     *
     * @param i
     * @return
     */
    public static int cheng(int i) {
        if (i == 1) {
            return 1;
        }
        return i * cheng(i - 1);
    }


    /**
     * 带开始和结束标志的累乘
     *
     * @param end
     * @param start
     * @return
     */
    public static int sumWithStart(int end, int start) {
        if (end == start) {
            return start;
        }
        return end + sumWithStart(end - 1, start);
    }

    /**
     * 带开始和结束标志的累加
     *
     * @param end
     * @param start
     * @return
     */
    public static int chengWithStart(int end, int start) {
        if (end == start) {
            return start;
        }
        return end * chengWithStart(end - 1, start);
    }

    /**
     * 累加Fib(n)=Fib(n-1)+Fib(n-2)
     * （兔子繁殖问题）斐波那契数列
     *
     * @param i
     * @return
     */
    public static int fb(int i) {
        if (i <= 1) {
            return i;
        }
        return fb(i - 1) + fb(i - 2);

    }

    /**
     * 累加Fib(n)=Fib(n-1)+Fib(n-2)
     * （兔子繁殖问题）斐波那契数列
     * 非递归实现
     *
     * @param n
     * @return
     */
    public static int fb2(int n) {
        int a = 1;
        int b = 1;
        int i = 2;
        int result = 0;
        if (n <= 2) {
            return 1;
        }
        while (i < n) {
            result = a + b;
            i++;
            a = b;
            b = result;
        }
        return result;

    }


    public static void main(String[] args) {

        System.out.println("计算结果：" + sum(100) + "!");
        System.out.println("计算结果：" + sumWithStart(100, -3) + "!");
        System.out.println("计算结果：" + cheng(10) + "!");
        System.out.println("计算结果：" + cheng(4) + "!");
        System.out.println("计算结果：" + cheng(10) / cheng(4) + "!");

        System.out.println("计算结果：" + chengWithStart(10, 5) + "!");
        log.info("第[{}]]月的兔兔数量是[{}]", 9, fb(9));
        log.info("非递归实现兔子问题，第[{}]]月的兔兔数量是[{}]", 9, fb2(9));


    }
}
