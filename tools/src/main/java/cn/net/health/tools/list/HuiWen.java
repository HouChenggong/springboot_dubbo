package cn.net.health.tools.list;

public class HuiWen {

    /**
     * 回文数，核心思想是把 121 1221都转换成 奇数的字符串，如1&2&1   1  & 2 &  2 &    1
     * <p>
     * <p>
     * 好吧效果堪忧
     * 执行用时 :19 ms, 在所有 java 提交中击败了41.03%的用户
     * 内存消耗 :40.5 MB, 在所有 java 提交中击败了73.14%的用户
     *
     * @param x
     * @return
     */
    public static boolean isPalindrome(int x) {

        String str = String.valueOf(x);
        int len = str.length();
        if (len == 1) {
            return true;
        }
        String result = "";
        for (int i = 0; i < len - 1; i++) {
            result += str.charAt(i) + "&";
        }
        result = result + str.charAt(len - 1);
        len = result.length();
        int start = len / 2;
        int end = start;
        while (start >= 0 || end <= len - 1) {
            if (result.charAt(start) == result.charAt(end)) {
                end++;
                start--;
            } else {
                return false;
            }
        }
        return true;

    }


    /**
     * 做下优化，只对偶数的进行转换
     * <p>
     * 好吧，没啥提升
     * 执行用时 :19 ms, 在所有 java 提交中击败了41.03%的用户
     * 内存消耗 :40.7 MB, 在所有 java 提交中击败了70.88%的用户
     *
     * @param x
     * @return
     */
    public static boolean isPalindromeTwo(int x) {

        String str = String.valueOf(x);
        int len = str.length();
        if (len == 1) {
            return true;
        }
        String result = "";
        if (len % 2 == 1) {
            result = str;
        } else {
            for (int i = 0; i < len - 1; i++) {
                result += str.charAt(i) + "&";
            }
            result = result + str.charAt(len - 1);
            len = result.length();
        }

        int start = len / 2;
        int end = start;
        while (start >= 0 || end <= len - 1) {
            if (result.charAt(start) == result.charAt(end)) {
                end++;
                start--;
            } else {
                return false;
            }
        }
        return true;

    }

    /**
     * 第三种方法，借鉴官方方案
     * 你能不将整数转为字符串来解决这个问题吗？
     * <p>
     * <p>
     * 第二个想法是将数字本身反转，然后将反转后的数字与原始数字进行比较，如果它们是相同的，那么这个数字就是回文。
     * 但是，如果反转后的数字大于 \text{int.MAX}int.MAX，我们将遇到整数溢出问题。
     * <p>
     * 按照第二个想法，为了避免数字反转可能导致的溢出问题，为什么不考虑只反转 \text{int}int 数字的一半？
     * 毕竟，如果该数字是回文，其后半部分反转后应该与原始数字的前半部分相同。
     * <p>
     * <p>
     * <p>看，效率大了很多
     * 执行用时 :10 ms, 在所有 java 提交中击败了87.68%的用户
     * 内存消耗 :36.6 MB, 在所有 java 提交中击败了93.79%的用户
     *
     * @param x
     */
    public static boolean isPalindromeThree(int x) {

        /**
         * 如果是一位数字，直接返回true
         */
        if (x >= 0 && x <= 9) {
            return true;
        }
        /**
         * len 是数字的长度，temp也是为了计算数字长度的中间临时变量
         */
        int len = 0;
        int temp = x;
        while (temp > 0) {
            temp = temp / 10;
            len++;
        }

        /**
         * erFenLen是len的一半，用来取后面一半的数字即tempX
         */
        int erFenLen = len / 2;
        int tempX = 0;
        while (erFenLen > 0) {
            tempX = tempX * 10 + x % 10;
            x = x / 10;
            erFenLen--;
        }
        /**
         * tempY是用来计算前面一半的数字，如果是121奇数类型的，要除10
         */
        int tempY = 0;
        if (len % 2 == 1) {
            tempY = x / 10;
        } else {
            tempY = x;
        }

        if (tempX == tempY) {
            return true;
        }
        return false;

    }


    /**
     * 感觉还能进行优化，我们上面为什么先要遍历一遍计算长度呢？为什么不直接计算呢？
     * <p>
     * <p>
     * <p>
     * 执行用时 :9 ms, 在所有 java 提交中击败了99.03%的用户
     * 内存消耗 :35.8 MB, 在所有 java 提交中击败了97.39%的用户
     *
     * @param x
     */
    public static boolean isPalindromeFour(int x) {

        /**
         * 如果是一位数字，直接返回true
         * 或者数字大于等于10，末尾为0直接返回
         */
        if ((x >= 0 && x <= 9)) {
            return true;
        }
        if ((x % 10 == 0 && x / 10 > 0)) {
            return false;
        }
        int hou = 0;


        while (x > hou) {
            hou = hou * 10 + x % 10;
            x = x / 10;

        }

        /**
         * 数字是奇数个的时候需要除以10
         * 这个因为如果是121，返回的x=1,hou=12，除以10看看等不等就完事了
         */
        return x == hou || x == hou / 10;

    }

    public static void main(String[] args) {
//        System.out.println(isPalindrome(10));
//        System.out.println(isPalindromeTwo(10));
//        System.out.println(isPalindromeThree(11));
        System.out.println(isPalindromeFour(10));
        System.out.println(isPalindromeFour(11));
        System.out.println(isPalindromeFour(21));
        System.out.println("----");
        System.out.println(isPalindromeFour(121));
        System.out.println(isPalindromeFour(123));
        System.out.println(isPalindromeFour(321));
        System.out.println("----");
        System.out.println(isPalindromeFour(1221));
        System.out.println(isPalindromeFour(1231));
        System.out.println(isPalindromeFour(1321));
        System.out.println(121 / 10);
    }

}
