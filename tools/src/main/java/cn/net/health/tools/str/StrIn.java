package cn.net.health.tools.str;

import java.util.Arrays;
import java.util.List;

public class StrIn {
    public static int strStr(String haystack, String needle) {
        int result = -1;
        char[] haystackChar = haystack.toCharArray();
        char[] needleChar = needle.toCharArray();
        // 如果目标字符串为空 返回0
        if (needleChar.length == 0) {
            return 0;
        }
        // 遍历源字符串
        for (int i = 0; i < haystackChar.length; i++) {
            // 判断当前字符是否和目标第一个字符相同
            if (haystackChar[i] == needleChar[0]) {
                // 如果源字符当前索引后面的长度没有目标字符串长 则不匹配,跳过当前循环
                if (i + needleChar.length > haystackChar.length) {
                    continue;
                }
                // 遍历目标字符串, 和源字符串当前索引位置的字符串开始对比 都想同则return
                for (int j = 0; j < needleChar.length; j++) {
                    result = i;
                    if (haystackChar[i + j] != needleChar[j]) {
                        result = -1;
                        break;
                    }
                }
                if (result > -1) {
                    return result;
                }
            }
        }
        return result;
    }
    /**
     * 辗转相除法的递归调用求两个数的最大公约数
     * @param x 其中一个数
     * @param y 其中另一个数
     * @return 递归调用，最终返回最大公约数
     */
    public static int gcd(int x, int y) {
        return y == 0 ? x : gcd(y , x % y);
    }
    /**
     * 求n个数的最大公约数
     * @param z 数据个数
     * @return 递归调用，最终返回最大公约数
     */
    public static int ngcd(List<Integer> target , int z) {
        if(z == 1) {
            return target.get(0);//真正返回的最大公约数
        }
        //递归调用，两个数两个数的求
        return gcd(target.get(z - 1) , ngcd(target , z - 1));
    }

    public static void main(String[] args) {
        List<Integer > arr = Arrays.asList( 10,4,6);
        System.out.println(ngcd(arr,3));
    }


}
