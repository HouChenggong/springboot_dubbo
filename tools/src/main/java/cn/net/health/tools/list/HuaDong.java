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
        test();

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
