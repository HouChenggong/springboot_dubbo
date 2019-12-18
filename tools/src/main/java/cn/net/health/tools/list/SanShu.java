package cn.net.health.tools.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/16 9:29
 */
public class SanShu {

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList();
        int len = nums.length;
        if (nums == null || len < 3) {
            return ans;
        }
        Arrays.sort(nums); // 排序
        for (int i = 0; i < len; i++) {
            if (nums[i] > 0) {
                break; // 如果当前数字大于0，则三数之和一定大于0，所以结束循环
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 去重
            }
            int L = i + 1;
            int R = len - 1;
            while (L < R) {
                int sum = nums[i] + nums[L] + nums[R];
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[L], nums[R]));
                    while (L < R && nums[L] == nums[L + 1]) {
                        L++; // 去重
                    }
                    while (L < R && nums[R] == nums[R - 1]) {
                        R--; // 去重
                    }
                    L++;
                    R--;
                } else if (sum < 0) {
                    L++;
                } else if (sum > 0) {
                    R--;
                }
            }
        }
        return ans;

    }


    /**
     * 力扣16. 最接近的三数之和
     * https://leetcode-cn.com/problems/3sum-closest/
     * 击败63.64%的用户
     *
     * @param nums
     * @param target
     * @return
     */
    public static int threeSum(int[] nums, Integer target) {

        int len = nums.length;
        if (nums == null || len < 3) {
            return 0;
        }
        Arrays.sort(nums); // 排序
        int cha = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < len; i++) {

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 去重
            }
            int L = i + 1;
            int R = len - 1;
            while (L < R) {
                int sum = nums[i] + nums[L] + nums[R];

                if (Math.abs(sum - target) < Math.abs(cha - target)) {
                    cha = sum;
                }
                if (sum < target) {
                    L++;
                    while (L < R - 2 && nums[L] == nums[L + 1]) {
                        L++; // 去重，但是不能去的太过分，一定要判断是否L<R-2
                    }
                } else if (sum > target) {
                    R--;
                    while (L < R - 2 && nums[R] == nums[R - 1]) {
                        R--; // 去重
                    }
                } else {
                    return target;
                }
            }
        }
        return cha;

    }


    public static void main(String[] args) {
        int[] arr = new int[]{-1, 0, 1, 2, -1, 2, -4};
        int[] arr2 = new int[]{-1, 0, 1, 1, 55};

        System.out.println(threeSum(arr));
        System.out.println(threeSum(arr2, 1));
    }


}
