package cn.net.health.leetcode;

import java.util.Arrays;
import java.util.Stack;

public class Lt425 {
    public static Boolean arr(String s) {

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push('(');
            }
            if (s.charAt(i) == ')') {
                if (stack.size() == 0) {
                    return false;
                } else {
                    stack.pop();
                }
            }
            if(s.charAt(i)=='*'){
                
            }
        }
        if (stack.size() > 0) {
            return false;
        }
        return true;
    }


    public int[][] merge(int[][] intervals) {
        // 先按照区间起始位置排序
        Arrays.sort(intervals, (v1, v2) -> v1[0] - v2[0]);
        // 遍历区间
        int[][] res = new int[intervals.length][2];
        int idx = -1;
        for (int i = 0; i < intervals.length; i++) {
            // 如果结果数组是空的，或者当前区间的起始位置 > 结果数组中最后区间的终止位置，
            // 则不合并，直接将当前区间加入结果数组。
            if (idx == -1 || intervals[i][0] > res[idx][1]) {
                res[++idx] = intervals[i];
            } else {
                // 反之将当前区间合并至结果数组的最后区间
                res[idx][1] = Math.max(res[idx][1], intervals[i][1]);
            }
        }
        return Arrays.copyOf(res, idx + 1);
    }


    public int merge2(int[][] intervals) {
        // 先按照区间起始位置排序
        Arrays.sort(intervals, (v1, v2) -> v1[0] - v2[0]);
        // 遍历区间

        int maxOne = 1;
        int arr[] = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            // 如果结果数组是空的，或者当前区间的起始位置 > 结果数组中最后区间的终止位置，
            // 则不合并，直接将当前区间加入结果数组。
            if ((intervals[i][0] > arr[0] && intervals[i][1] > arr[1])) {
                maxOne++;

            } else {
                arr = intervals[i];
            }
        }
        return maxOne;
    }

    public static int lengthOfLIS(int[] nums) {
        int len = nums.length;
        if (len <= 1) {
            return len;
        }
        int max = 1;


        for (int i = 0; i < len; i++) {
            int one = nums[i];
            int maxOne = 1;
            int low = one;
            if (i == 0 || one < nums[i - 1]) {
                int j = i + 1;
                while (j < len) {
                    if (nums[j] > low) {
                        maxOne++;
                        low = nums[j];
                    }
                    j++;
                }
            }
            max = Math.max(maxOne, max);
        }
        return max;


    }


    public static void main(String[] args) {
        int arr[] = new int[]{10, 9, 2, 5, 3, 4};
        System.out.println(lengthOfLIS(arr));

    }
}
