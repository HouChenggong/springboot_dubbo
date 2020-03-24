package cn.net.health;

import java.util.Arrays;

public class Lt40 {
    public static int[] getLeastNumbers(int[] arr, int k) {
        int len = arr.length;
        if (len == 0) {
            return new int[]{};
        }
        int[] res = new int[10000];
        for (int i = 0; i < len; i++) {
            res[arr[i]]+=1;
        }
        int result[] = new int[k];
        int num = 0;
        for (int i = 0; i < 10000; i++) {

            while (num < k && res[i] > 0) {




                num++;
                res[i]-=1;
            }
        }
        return result;
    }



    public static int[] getLeastNumbers2(int[] arr, int k) {
        quickselect(arr, 0, arr.length - 1, k);
        return Arrays.copyOfRange(arr, 0, k);
    }

    private static void quickselect(int[] nums, int start, int end, int k) {
        while (start < end) {
            // 这边做了了小优化，三数取中～前两步先确定最后一个一定是最大的，然后只要把中间那个数放第一个位置即可
            // 如果你嫌麻烦可以直接 int pivot = nums[start];
            int mid = start + (end - start) / 2;
            if (nums[start] > nums[end]) swap(nums, start, end);
            if (nums[mid] > nums[end]) swap(nums, mid, end);
            if (nums[mid] > nums[start]) swap(nums, mid, start);
            int pivot = nums[start];

            int i = start, j = end;
            while (i <= j) {
                //升序～
                while (i <= j && nums[i] < pivot) i++;
                while (i <= j && nums[j] > pivot) j--;
                if (i <= j) {
                    swap(nums,i,j);
                    i++;
                    j--;
                }
            }
            if (i >= k) {
                end = i - 1;
            } else {
                start = i;
            }
        }
    }

    private static  void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
    public static void main(String[] args) {
        int arr[]=new int[]{1,2,3,4,5,2,1,2,3};
        System.out.println(getLeastNumbers(arr,4));
    }
}
