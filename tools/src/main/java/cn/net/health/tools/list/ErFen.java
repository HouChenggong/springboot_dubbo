package cn.net.health.tools.list;

/**
 * @author xiyou
 * 二分法详细解答
 * https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/solution/er-fen-cha-zhao-suan-fa-xi-jie-xiang-jie-by-labula/
 */
public class ErFen {
    /**
     * 寻找一个数
     *
     * @param nums
     * @param target
     * @return
     */
    static int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1; // 注意

        /**
         * 为什么要<=，为了防止【2，2】的情况，小于的话就把2漏掉了
         */
        while (left <= right) {
            int mid = (right + left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1; // 注意
            } else if (nums[mid] > target) {
                right = mid - 1; // 注意
            }
        }
        return -1;
    }


    /**
     * 寻找左侧边界的二分搜索
     *
     * @param nums
     * @param target
     * @return
     */
    static int left_bound(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length; // 注意

        while (left < right) { // 注意
            int mid = mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid; // 注意
            }
        }
        //上面while循环真正的含义是：nums数组小于target的数有几个，如果全部都小于返回-1，

        // target 比所有数都大
        if (left == nums.length) {
            return -1;
        }
        //如果算出的不存在返回-1
        return nums[left] == target ? left : -1;
    }


    /**
     * 寻找右侧边界的二分查找
     *
     * @param nums
     * @param target
     * @return
     */
    static int right_bound(int[] nums, int target) {
        if (nums.length == 0) return -1;
        int left = 0, right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                left = mid + 1; // 注意
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid;
            }
        }
        /**
         * 因为left的范围是【1，len】
         */
        if (left == 0) {
            return -1;
        }
        return nums[left - 1] == target ? (left - 1) : -1;
    }


    public static int[] searchRange(int[] nums, int target) {

        int arr[] = new int[]{-1, -1};
        arr[0] = left_bound(nums, target);
        arr[1] = right_bound(nums, target);
        return arr;
    }

    public static void main(String[] args) {
        int arr[] = new int[]{5, 7, 7, 8, 8, 10};
        System.out.println(binarySearch(arr, 6));
        System.out.println(left_bound(arr, 6));
        System.out.println(right_bound(arr, 6));
        int arr2[] = searchRange(arr, 8);
        int arr3[] = searchRange(arr, 6);
        System.out.println(arr2[0] + "..." + arr2[1]);
        System.out.println(arr3[0] + "..." + arr3[1]);
    }

}
