package cn.net.health.tools.list;

import com.google.common.util.concurrent.RateLimiter;

import java.util.LinkedList;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/9 10:40
 */
public class HuaDong {
    //服务访问次数，可以放在Redis中，实现分布式系统的访问计数
    static Long counterNum = 0L;
    //使用LinkedList来记录滑动窗口的10个格子。
    LinkedList<Long> ll = new LinkedList<Long>();

    public static void main(String[] args) {
//        test();
        int arr[] = new int[]{1, 3, 5, 3, 5, 7, 2, 3};
        int arr2[] = new int[]{1, -2};
//        System.out.println(maxSlidingWindow(arr,3));
        System.out.println(maxSlidingWindow(arr2, 1));
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 1) {
            //如果k=1，直接返回原来的数组
            return nums;
        }
        //数组长度
        int length = nums.length;
        //要返回的结果
        int res[] = new int[length - k + 1];
        //一些特殊判断
        if (nums == null || length == 0 || k <= 0 || k > length) {
            return new int[0];
        }
        //初始化
        int left = 0, right = 0, max = nums[0];
        //返回结果最大值的索引
        int num = 0;
        while (right < nums.length) {
            //遍历每次滑动的小窗口最大值,因为下面要进行right++,所以right < nums.length - 1，而不是小于nums.length
            while (right < nums.length - 1 && right - left < k - 1) {
                right++;
                if (nums[right] > max) {
                    max = nums[right];
                }
            }
            res[num] = max;
            num++;
            left++;
            //如果right是最后一位的话，直接返回，因为如果不返回，会有一种情况导致还要重新判断，比如1，7，3，5，3
            //此时right是最后一位，恰巧7是前面的最大值即将被移除，就会导致重新进行判断
            if (right == length - 1) break;
            //如果左边要划掉的值正好是最大值的话，要重新计算窗口中的最大值
            if (nums[left - 1] == max) {
                right = left;
                max = nums[left];
            }
        }
        return res;

    }

    private void doCheck() {
        counterNum++;
        while (true) {
            ll.addLast(counterNum);

            if (ll.size() > 10) {
                ll.removeFirst();
            }

            //比较最后一个和第一个，两者相差一秒
            if ((ll.peekLast() - ll.peekFirst()) > 100) {
                //To limit rate
                System.out.println(".....");
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(",");
        }
    }

    public static void test() {
        /**
         * 创建一个限流器，设置每秒放置的令牌数：2个。速率是每秒可以2个的消息。
         * 返回的RateLimiter对象可以保证1秒内不会给超过2个令牌，并且是固定速率的放置。达到平滑输出的效果
         */
        RateLimiter r = RateLimiter.create(10);

        while (true) {
            /**
             * acquire()获取一个令牌，并且返回这个获取这个令牌所需要的时间。如果桶里没有令牌则等待，直到有令牌。
             * acquire(N)可以获取多个令牌。
             */
            System.out.println(r.acquire());
        }
    }

}
