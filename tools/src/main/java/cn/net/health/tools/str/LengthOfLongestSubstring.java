package cn.net.health.tools.str;

import java.util.HashMap;
import java.util.Map;

public class LengthOfLongestSubstring {


    /**
     * 第一个版本：
     * 思路： 每次发现不一样的，maxLen+1,同时把原来的字符串裁剪
     * 比如ABCDECCC，第一次裁剪变成DECCC，然后接着从E遍历
     * <p>
     * <p>
     * 但是效率堪忧：
     * 执行用时 :187 ms在所有 Java 提交中击败了10.57%的用户
     * 内存消耗 :39.9 MB, 在所有 Java 提交中击败了75.87%的用户
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        int len = s.length();
        if (len <= 1) {
            return len;
        }
        int maxLen = 0;
        String oneValue = "";
        String maxStr = "";
        int oneMaxLen = 0;
        for (int i = 0; i < s.length(); i++) {

            oneValue = String.valueOf((s.charAt(i)));
            if (maxStr.contains(oneValue)) {
                i = maxStr.indexOf(oneValue) + 1;
                maxStr = String.valueOf(s.charAt(i));
                s = s.substring(i, s.length());
                i = 0;
            } else {
                maxStr += oneValue;
                oneMaxLen = maxStr.length();
                if (oneMaxLen >= maxLen) {
                    maxLen = oneMaxLen;
                }
            }
        }
        return maxLen;
    }


    /**
     * 第2个版本：
     * 思路： 每次发现不一样的，maxLen+1,同时把原来的字符串裁剪
     * 比如ABCDECCC，第一次裁剪变成DECCC，然后就不从E遍历了，而是从找到C的下一个位置遍历，DEC，的下一个C遍历
     * 好吧，效果还是很低，但是有进步了
     * <p>
     * <p>
     * 但是效率堪忧：
     * 执行用时 :81 ms在所有 Java 提交中击败了20%的用户
     * 内存消耗 :38.3MB, 在所有 Java 提交中击败了89.05%的用户
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring2(String s) {
        int len = s.length();
        if (len <= 1) {
            return len;
        }
        int maxLen = 0;
        String oneValue = "";
        String maxStr = "";
        int oneMaxLen = 0;
        int index = 0;
        for (int i = 0; i < s.length(); i++) {

            oneValue = String.valueOf((s.charAt(i)));
            if (maxStr.contains(oneValue)) {
                i = maxStr.indexOf(oneValue) + 1;
                maxStr = String.valueOf(s.charAt(i));
                s = s.substring(i, s.length());
                index = s.indexOf(oneValue, 0);
                maxStr = s.substring(0, index + 1);

                i = index;
            } else {
                maxStr += oneValue;
                oneMaxLen = maxStr.length();
                if (oneMaxLen >= maxLen) {
                    maxLen = oneMaxLen;
                }
            }
        }
        return maxLen;
    }

    /**
     * 利用滑动窗口，只是把上面的思路换成了HashMap而已
     * <p>
     * <p>
     * <p>
     * 执行用时 :16 ms在所有 Java 提交中击败了72%的用户
     * 内存消耗 :37.3MB, 在所有 Java 提交中击败了93的用户
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstringHashMap(String s) {
        int len = s.length();
        int maxLen = 0;
        Map<Character, Integer> map = new HashMap<>();
        int left = 0;

        for (int right = 0; right < len; right++) {
            if (map.containsKey(s.charAt(right))) {
                left = Math.max(map.get(s.charAt(right)) + 1, left);
            }
            map.put(s.charAt(right), right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    /**
     * 利用滑动窗口，只是把上面的思路换成了right+1
     * 因为这么做其实是有优势的，不信的话请看下面的一个方法
     * <p>
     * <p>
     * <p>
     * 执行用时 :16 ms在所有 Java 提交中击败了72%的用户
     * 内存消耗 :37.3MB, 在所有 Java 提交中击败了93的用户
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstringHashMap2(String s) {
        int len = s.length();
        int maxLen = 0;
        Map<Character, Integer> map = new HashMap<>();
        int left = 0;

        for (int right = 0; right < len; right++) {
            if (map.containsKey(s.charAt(right))) {
                left = Math.max(map.get(s.charAt(right)), left);
            }
            map.put(s.charAt(right), right + 1);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    /**
     * 利用滑动窗口，如果这个方式的话你用它的坐标来存储值的话，会有一个问题，就是坐标是0的重复值
     * 比如abc，用坐标存储就会少一个值
     * <p>
     * <p>
     * <p>
     * 执行用时 :16 ms在所有 Java 提交中击败了72%的用户
     * 内存消耗 :37.3MB, 在所有 Java 提交中击败了93的用户
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstringArr(String s) {
        int len = s.length();
        if (len <= 1) {
            return len;
        }
        int maxLen = 0;
        int arr[] = new int[256];
        int left = 0;
        for (int right = 0; right < len; right++) {
            left = Math.max(arr[s.charAt(right)], left);
            arr[s.charAt(right)] = right + 1;
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(lengthOfLongestSubstringHashMap("bbbbb"));
        System.out.println(lengthOfLongestSubstringArr("abcabcbb"));
    }
}
