package cn.net.health.tools.list;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/13 15:19
 * 力扣14题： https://leetcode-cn.com/problems/longest-common-prefix/
 */
public class ZuiChang {

    /**
     * 最简单的方式;
     * 数组列表从前往后两两比较，找到最长的前缀
     * 如果出现common.length=0直接返回
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        int strLen = strs.length;
        if (strLen == 0) {
            return "";
        }
        String common = strs[0];
        for (int i = 1; i < strLen; i++) {
            while (strs[i].indexOf(common) != 0) {
                common = common.substring(0, common.length() - 1);
            }
            if (common.length() == 0) {
                return "";
            }
        }
        return common;
    }


    /**
     * 但是如果列表是这样的[12345678,1234566,12345667,...,...,...,1]
     * 就会出现一个情况那就是前面进行了非常复制的判断，但是后面只有很少或者NULL的字符串，那岂不是前面的判断全都都白费了
     * 所以提出下面的观点：
     * 一：先遍历一遍，找到最短的，如果最短的为NULL直接返回
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefixTwo(String[] strs) {
        int strLen = strs.length;
        if (strLen == 0) {
            return "";
        }
        int minLen = strs[0].length();
        int index = 0;
        for (int i = 1; i < strLen; i++) {
            if (minLen > strs[i].length()) {
                minLen = strs[i].length();
                index = i;
            }
        }
        String common = strs[index];
        for (int i = 0; i < strLen; i++) {
            while (strs[i].indexOf(common) != 0) {
                common = common.substring(0, common.length() - 1);
            }
            if (common.length() == 0) {
                return "";
            }
        }
        return common;
    }


    /**
     * @param strs
     * @return
     */
    public String longestCommonPrefixThree(String[] strs) {

        return null;
    }


    /**
     * 官方给出的复杂度最高时N*M的算法
     *比如列表是[abc,abcd,abd,add,a]
     * i == strs[j].length() 这个是判断比如说第一次遍历a ,如果a和别人的长度一样，说明可以直接截取了
     * strs[j].charAt(i) != c 这个是判断比如说第一次遍历a，但是别人的相同位置不是a，也可以截取了
     * @param strs
     * @return
     */
    public static String longestCommonPrefixFour(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    public static void main(String[] args) {
        String arr[] = new String[]{"abc", "abcd", "abd", "a"};
        System.out.println(longestCommonPrefixTwo(arr));
        System.out.println(longestCommonPrefix(arr));
        System.out.println(longestCommonPrefixFour(arr));
    }

}
