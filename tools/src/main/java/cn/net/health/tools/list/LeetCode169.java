package cn.net.health.tools.list;

import java.util.*;

/**
 * @author xiyou
 */
public class LeetCode169 {
    public static int getVal(String str) {
        int count = 0;//用于判断相邻两个数是否相同
        char now = str.charAt(0);//用于记录多数的那个数
        for (int i = 0; i < str.length(); i++) {
            if (count == 0) {
                now = str.charAt(i);
                count += 1;
            } else {
                if (now == str.charAt(i)) {
                    count += 1;
                } else {
                    count -= 1;
                }
            }
        }
        //循环结束now就是所求的多数

        int countNum = 0;//多数出现的次数
        for (int j = 0; j < str.length(); j++) {
            if (str.charAt(j) == now) {
                countNum++;
            }
        }
        return countNum;


    }

    /**
     * 多数投票法
     *
     * @param nums
     * @return
     */
    public static int majorityElement(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return -1;
        }
        int count = 0;
        int target = nums[0];
        for (int i = 0; i < len; i++) {
            if (nums[i] == target) {
                count++;
            } else {
                count--;
                if (count == 0) {
                    target = nums[i];
                    count++;
                }
            }
        }
        return target;
    }

    public int lengthOfLIS(int[] nums) {
        int len = nums.length;
        if (len <= 1) {
            return len;
        }
        int maxLen = 0;
        int minVal = nums[0];
        int maxVal = nums[0];
        for (int i = 1; i < len; i++) {
            if (nums[i] > nums[i - 1]) {
                maxLen++;
                maxVal = Math.max(maxVal, nums[i]);
            } else {
                if (nums[i] <= minVal) {
                    minVal = Math.max(minVal, nums[i]);
                } else if (nums[i] >= maxVal) {

                }
                minVal = Math.max(minVal, nums[i]);

            }
        }
        return 1;
    }

    public static List<Integer> luckyNumbers(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        if (m > 50) {
            return null;
        }
        if (n == 0) {
            return null;
        }
        List<Integer> res = new ArrayList<>(8);

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int j = 0;
            int minOne = matrix[i][0];
            while (j < n) {
                if (matrix[i][j] < minOne) {
                    minOne = matrix[i][j];
                }
                j++;
            }
            map.put(i, minOne);
        }
        for (int j = 0; j < n; j++) {
            int i = 0;
            int num = 0;
            int maxOne = matrix[i][j];
            while (i < m) {
                if (matrix[i][j] >= maxOne) {
                    maxOne = matrix[i][j];
                    num = i;
                }
                i++;
            }
            System.out.println(map.get(num));
            if (map.containsKey(num) && map.get(num) == maxOne) {
                res.add(matrix[num][j]);
            }
        }

        return res;


    }

    public static String compressString(String S) {
        int len = S.length();
        if (len == 0 || len > 50000) {
            return null;
        }
        if (len == 1) {
            return S;
        }
        char[] arr = S.toCharArray();
        StringBuffer buffer = new StringBuffer();
        int num = 1;
        for (int i = 1; i < len; i++) {
            if (arr[i] == arr[i - 1]) {
                num++;
                if (i == len - 1) {
                    buffer.append(arr[i]).append(num);
                }
            } else {

                buffer.append(arr[i - 1]).append(num);
                num = 1;
            }
        }
        if (buffer.length() >= len) {
            return S;
        }
        return buffer.toString();
    }

    public static int maxProfit2(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        //第一天买入的最大收益是-prices[0]
        int oldBuy = -prices[0];
        //昨天无操作
        int oldNothing = 0;
        //第一天卖出的最大收益是0
        int oldSell = 0;

        int nowSell, nowBuy, nowNothing = 0;
        for (int i = 0; i < prices.length; i++) {
            //今天卖出=昨天买入，今天卖出，或者昨天无操作但是今天卖出
            nowSell = prices[i] + Math.max(oldBuy, oldNothing);
            //今天买入=昨天买入，今天无操作，或者昨天卖出，今天无操作
            nowBuy = Math.max(oldSell, oldBuy) - prices[i];
            //今天无操作=昨天无操作，或者昨天卖出，或者昨天买入
            nowNothing = Math.max(oldNothing, oldBuy);
            nowNothing = Math.max(nowNothing, oldSell);
            oldBuy = nowBuy;
            nowNothing = nowNothing;
            oldSell = nowSell;
        }

        return Math.max(oldSell, oldNothing);
    }


    public static int maxProfit(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        //第一天持有的最大收益是-prices[0]
        int oldHave = -prices[0];
        //第一天不持有，而且什么也不做的最大收益是0
        int oldNotHaveButDoNothing = 0;
        //第一天卖出的最大收益是0
        int oldSell = 0;

        int nowSell, nowHave, nowNotHaveButDoNothing = 0;
        for (int i = 0; i < prices.length; i++) {
            //今天卖出
            nowSell = prices[i] + oldHave;
            //今天没有而且今天没有进行任何操作=昨天没有而且没有进行操作,或者昨天卖了,今天没有任何操作
            nowNotHaveButDoNothing = Math.max(oldNotHaveButDoNothing, oldSell);
            //今天有=昨天就有,或者今天买入
            nowHave = Math.max(oldHave, oldNotHaveButDoNothing - prices[i]);

            oldHave = nowHave;
            oldNotHaveButDoNothing = nowNotHaveButDoNothing;
            oldSell = nowSell;
        }

        return Math.max(oldSell, oldNotHaveButDoNothing);
    }


    public static int countCharacters(String[] words, String chars) {
        int len = chars.length();
        if (len == 0) {
            return 0;
        }
        int[] arr = new int[26];
        int oneChar = 0;
        for (int i = 0; i < len; i++) {
            oneChar = (int) chars.charAt(i) - 'a';
            arr[oneChar] += 1;
        }

        int total = 0;
        for (String one : words) {
            int[] oneArr = new int[26];
            for (int i = 0; i < one.length(); i++) {
                oneChar = (int) one.charAt(i) - 'a';
                oneArr[oneChar] += 1;
            }
            boolean flag = true;
            for (int i = 0; i < 26; i++) {
                if (oneArr[i] > arr[i]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                total += one.length();
            }


        }
        return total;

    }

    public static int maxProfit_with_cool22(int[] prices, int fee) {
        int len = prices.length;
        if (len <= 1) {
            return 0;
        }
        int total = 0;
        int minOne = prices[0];
        int flag = 1;
        for (int i = 1; i < len; i++) {
            if (prices[i] > minOne) {
                if (prices[i] - minOne > fee) {
                    total += prices[i] - minOne - fee;
                    System.out.println(total + "____" + i);
                    flag = 0;
                    minOne = prices[i];
                }
            } else {
                if (flag == 1) {
                    minOne = Math.min(minOne, prices[i]);
                } else {
                    minOne = prices[i];
                }

            }

        }
        return total;

    }


    public static int maxProfit_with_cool(int[] prices) {
        int len = prices.length;
        if (len <= 1) {
            return 0;
        }
        int total = 0;
        for (int k = 0; k < len; k++) {
            int totalHead = 0;
            int minHead = prices[0];
            for (int i = 0; i < k; i++) {
                if (prices[i] >= minHead) {
                    totalHead = Math.max(totalHead, prices[i] - minHead);
                } else {
                    minHead = prices[i];
                }
            }
            int totalTail = 0;
            int minTail = prices[k];
            for (int i = k; i < len; i++) {
                if (prices[i] >= minTail) {
                    totalTail = Math.max(totalTail, prices[i] - minTail);
                } else {
                    minTail = prices[i];
                }
            }
            System.out.println("k=" + k + "___totalHead" + totalHead + "__tail" + totalTail);
            total = Math.max(total, totalHead + totalTail);
        }
        return total;
    }


    public static void main(String[] args) {
        int arr[] = new int[]{1, 3, 7,5,10,3};
        System.out.println(maxProfit_with_cool22(arr, 3));
    }
}
