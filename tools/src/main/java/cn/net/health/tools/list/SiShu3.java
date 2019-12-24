package cn.net.health.tools.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/24 9:49
 * 力扣18 4数之和
 * https://leetcode-cn.com/problems/4sum/
 */
public class SiShu3 {

    /**
     * 击败了99.55的用户
     *
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>(8);
        int len = nums.length;
        if (len < 4) {
            return result;
        }
        Arrays.sort(nums);

        int minValue = nums[0] + nums[1] + nums[2] + nums[3];
        int maxValue = nums[len - 1] + nums[len - 2] + nums[len - 3] + nums[len - 4];
        if (maxValue < target || minValue > target) {
            return result;
        }
        int left = 2;
        int right = len - 1;
        //第一个固定值的值
        int oneValue = 0;
        //第2个固定值的值
        int twoValue = 0;
        int fourNumSum = 0;
        for (int one = 0; one <= len - 4; one++) {
            oneValue = nums[one];
            if (one > 0 && nums[one] == nums[one - 1]) {
                continue;
            }
            for (int two = one + 1; two <= len - 3; two++) {
                if (two > one + 1 && nums[two] == nums[two - 1]) {
                    continue;
                }
                twoValue = nums[two];
                left = two + 1;
                right = len - 1;
                minValue = oneValue + twoValue + nums[two + 1] + nums[two + 2];
                maxValue = oneValue + twoValue + nums[len - 2] + nums[len - 1];
                if (maxValue < target || minValue > target) {
                    left = right;
                }
                while (left < right) {
                    fourNumSum = nums[left] + nums[right] + oneValue + twoValue;
                    if (fourNumSum > target) {
                        right--;
                        while (right > left && nums[right] == nums[right + 1]) {
                            right--;
                        }
                    } else if (fourNumSum < target) {
                        left++;
                        while (right > left && nums[left] == nums[left - 1]) {
                            left++;
                        }
                    } else {
                        List<Integer> resultOne = new ArrayList<>(4);
                        resultOne.add(oneValue);
                        resultOne.add(twoValue);
                        resultOne.add(nums[left]);
                        resultOne.add(nums[right]);
                        result.add(resultOne);

                        left++;
                        right--;
                        while (right > left && nums[right] == nums[right + 1]) {
                            right--;
                        }
                        while (right > left && nums[left] == nums[left - 1]) {
                            left++;
                        }
                    }
                }

            }


        }
        return result;

    }

    public static void main(String[] args) {
        //0[[-3,-2,2,3],[-3,-1,1,3],[-3,0,0,3],[-3,0,1,2],[-2,-1,0,3],[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
        int arr[] = new int[]{-3, -2, -1, 0, 0, 1, 2, 3};
        //0[[-1,-1,0,2]]
        //-1[[-4,0,1,2],[-1,-1,0,1]]
        int arr2[] = new int[]{-1, 0, 1, 2, -1, -4};
        int arr3[] = new int[]{1, -2, -5, -4, -3, 3, 3, 5};
        System.out.println(fourSum(arr, 0));
        System.out.println(fourSum(arr2, -1));
        System.out.println(fourSum(arr2, 0));
        System.out.println(fourSum(arr3, -11));


    }
}
