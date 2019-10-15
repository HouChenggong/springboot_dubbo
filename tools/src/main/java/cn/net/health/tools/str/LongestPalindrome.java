package cn.net.health.tools.str;

public class LongestPalindrome {
    /**
     * 方法A：两个字符串的公共最长部分
     * 1. 寻找回文数，可以用一个方法，比较反转后两个字符串的最长相同部分
     * 比如12345678987326
     * 反转过来就是62378987654321
     * 最长公共部分就是78987
     * 2.  然后问题就变成了寻找两个字符串的最长公共部分
     *
     * @param s
     * @return
     */


    /**
     * 还有一种方法B是：中心扩展法：
     * 其实就是假设一个字符串有回文，但是回文的中心点不能确定
     * 我们就从开始点找到最后一个点，总能找到回文数最大的中心点
     * 而且每一个点需要查询两次，因为回文数可能是偶数的，也可能是奇数的，
     * 去每一次奇数和偶数的最大值
     * <p>
     * <p>
     * 执行用时 :
     * 8 ms, 在所有 java 提交中击败了96.79%的用户
     * 内存消耗 :36.4 MB, 在所有 java 提交中击败了90.88%的用户
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        int len = s.length();
        if (len <= 1) {
            return s;
        }
        int oneLen = 0;
        int start = 0;
        int end = 0;
        for (int i = 0; i < len; i++) {
            int len1 = maxLen(s, i, i);
            int len2 = maxLen(s, i, i + 1);
            oneLen = Math.max(len1, len2);
            /**
             * 因为每一次找到回文数，如果当前oneLen 大于之前最大值，就替换start和end
             * 之所以不存之前的最大值，是因为，end-start就代表之前的最大值
             */
            if (oneLen > end - start) {
                start = i - (oneLen - 1) / 2;
                end = i + oneLen / 2;
            }
//            System.out.println("start" + start + "end" + end);
        }


        return s.substring(start, end + 1);
    }

    public static int maxLen(String s, int L, int R) {
        int right = R;
        int left = L;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            right++;
            left--;

        }
        return right - left - 1;
    }


    /**
     * 还有一种方法C是：中心扩展法简化版：
     * 因为上面的中心扩展法，还要区分奇数和偶数，能不能找到一个方法使所有的回文都是奇数或者偶数呢？
     * 数字中间加# 比如12，变为1#2 123变为1#2#3 都是奇数了，现在就需要遍历一遍了
     * 但是我现在的做法是maxLen2直接返回了一个字符串，导致时间特别慢，可以优化代码，让它和maxLen一样返回长度，然后计算就行
     *
     * @param s
     * @return
     */
    public static String longestPalindromeJian(String s) {
        int len = s.length();
        if (len <= 1) {
            return s;
        }
        StringBuffer str = new StringBuffer();
        for (int j = 0; j < len - 1; j++) {
            str.append(s.charAt(j) + "#");
        }
        str.append(s.charAt(len - 1));
        s = str.toString();
        len = s.length();
        String endStr = "";
        String oneStr = "";
        for (int i = 0; i < len; i++) {
            oneStr = maxLen2(s, i, i);
            if (oneStr.length() >= endStr.length()) {
                endStr = oneStr;
            }
        }

        return endStr;
    }

    public static String maxLen2(String s, int L, int R) {
        int right = R;
        int left = L;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            right += 1;
            left -= 1;
        }
        left += 1;
        right -= 1;

        return s.substring(left, right + 1).replaceAll("#", "");
    }

    public static void main(String[] args) {

        System.out.println(longestPalindrome("babadufdgd"));
        System.out.println(longestPalindromeJian("cbbd"));
    }

}
