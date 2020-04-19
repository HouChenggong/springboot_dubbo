package cn.net.health.leetcode;

public class Lt55 {
    public static boolean canJump(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == 0) {
                if (passZero(nums, i)) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    //判断是否能跳出当前0
    public static boolean passZero(int[] nums, int index) {
        for (int i = index; i >= 0; i--) {
            if (nums[i] > (index - i)) {
                return true;
            }
        }
        return false;
    }



    public static void main(String[] args) {
        int []arr =new int[]{0};
        System.out.println(canJump(arr));
    }

}



