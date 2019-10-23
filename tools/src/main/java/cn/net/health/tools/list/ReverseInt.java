package cn.net.health.tools.list;

public class ReverseInt {

    /**
     * https://leetcode-cn.com/problems/reverse-integer/submissions/
     * 整数反转
     * 思路是每次取最后一个数
     * 执行用时 :1 ms, 在所有 java 提交中击败了100.00%的用户
     * 内存消耗 :33.4 MB, 在所有 java 提交中击败了82.06%的用户
     *
     * @param x
     * @return
     */
    public static int reverse(int x) {
        long temp = 0;

        while (x != 0) {
            int pop = x % 10;
            temp = temp * 10 + pop;

            if (temp > Integer.MAX_VALUE || temp < Integer.MIN_VALUE) {
                return 0;
            }
            x /= 10;
        }
        return (int) temp;
    }


    /**
     * https://leetcode-cn.com/problems/string-to-integer-atoi/
     * <p>
     * 字符串转数字
     * <p>
     * 本来以为写的代码很复杂，结果：
     * 执行用时 :2 ms, 在所有 java 提交中击败了99.96%的用户
     * 内存消耗 :35.8 MB, 在所有 java 提交中击败了88.58%的用户
     *
     * @param str reuslt是最后的结果
     *            index是判断是否是正负数的
     * @return
     */
    public static int myAtoi(String str) {
        char[] arr = str.toCharArray();
        long result = 0;
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (index == 0) {
                if (arr[i] == '+') {
                    index = 1;
                } else if (arr[i] == '-') {
                    index = -1;
                } else if (arr[i] == ' ') {

                } else if (arr[i] >= '0' && arr[i] <= '9') {
                    index = 1;
                    result = result + arr[i] - ('0');
                } else {
                    return 0;
                }
            } else {
                if (arr[i] >= '0' && arr[i] <= '9') {
                    result = (arr[i] - ('0') + result * 10);
                    if (result > Integer.MAX_VALUE) {
                        if (index == 1) {
                            return Integer.MAX_VALUE;
                        } else {
                            return Integer.MIN_VALUE;
                        }

                    }
                } else {
                    if (index == -1) {
                        return -(int) result;
                    }
                    return (int) result;
                }
            }

        }
        if (index == -1) {
            return -(int) result;
        }
        return (int) result;
    }


    public static void main(String[] args) {
        System.out.println(reverse(-12345));
        System.out.println(myAtoi("2147483646"));
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
    }
}
