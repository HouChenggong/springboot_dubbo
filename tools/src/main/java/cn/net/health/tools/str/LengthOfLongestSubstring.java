package cn.net.health.tools.str;

public class LengthOfLongestSubstring {


    /**
     * 第一个版本：
     * 思路： 每次发现不一样的，maxLen+1,同时把原来的字符串裁剪
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

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }
}
