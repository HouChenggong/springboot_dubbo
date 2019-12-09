package cn.net.health.tools.list;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/9 9:36
 * 题目:https://leetcode-cn.com/problems/container-with-most-water/
 */
public class ChengShui {

    /**
     * 行用时 :4 ms, 在所有 java 提交中击败了80.60%的用户
     * 内存消耗 :39.9 MB, 在所有 java 提交中击败了92.44%的用户
     *
     * @param height
     * @return
     */
    public static int maxArea(int[] height) {

        int right = height.length;
        if (right < 1) {
            return 0;
        }
        int left = 0;
        int maxLen = 0;
        int oneCha = 0;
        right--;
        while (right > left) {
            oneCha = Math.min(height[right], height[left]);
            maxLen = Math.max(maxLen, oneCha * (right - left));
            if (height[left] <= height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxLen;

    }

    public static void main(String[] args) {
        int arr[] = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        int arr3[] = new int[]{10, 14, 10, 4, 10, 2, 6, 1, 6, 12};
        int arr2[] = new int[]{2, 1};
        int arr1[] = new int[]{1, 2};
        int arr4[] = new int[]{2, 3, 4, 5, 18, 17, 6};
        System.out.println(maxArea(arr2));
        System.out.println(maxArea(arr));
        System.out.println(maxArea(arr3));
        System.out.println(maxArea(arr1));
        System.out.println(maxArea(arr4));
    }


}
