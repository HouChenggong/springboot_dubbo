package cn.net.health.tools.list;


/**
 * 寻找两个有序数组的中位数
 * <p>
 * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
 */
public class FindMedianSortedArrays {


    /**
     * 思路:
     * 1. 对两个数组进行合并但是不去重 ，最后取中间的数即可
     * 2. 对两个数组进行合并去重 ，记录总共有多少数字，最后取中间的数即可
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int num = nums1.length + nums2.length;
        if (num == 0) {
            return 0;
        }
        int m = 0;
        int n = 0;
        int[] merge = new int[num];
        int index = 0;
        while (m < nums1.length && n < nums2.length) {
            if (nums1[m] <= nums2[n]) {
                merge[index++] = nums1[m++];
            } else {
                merge[index++] = nums2[n++];
            }
        }

        if (m == nums1.length) {
            for (int k = n; k < nums2.length; k++) {
                merge[index++] = nums2[k];
            }
        }
        if (n == nums2.length) {
            for (int k = m; k < nums1.length; k++) {
                merge[index++] = nums1[k];
            }
        }

        if ((num % 2) == 0) {
            return (double) (merge[num / 2 - 1] + merge[num / 2]) / 2;
        } else {
            return (double) merge[(num - 1) / 2];
        }
    }


    /**
     * 思路3：
     * 不进行合并，也不进行去重合并，我们只需要知道两个数组的总长度len ，然后对两个数组遍历
     * 遍历到中间的位置记录下即可，但是要分奇数和偶数的情况
     */


}
