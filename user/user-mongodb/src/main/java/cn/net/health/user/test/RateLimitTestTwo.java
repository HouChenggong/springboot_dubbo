package cn.net.health.user.test;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/9 14:41
 * RateLimiter的SmoothWarmingUp是带有预热期的平滑限流，它启动后会有一段预热期，逐步将分发频率提升到配置的速率。
 * 比如下面代码中的例子，创建一个平均分发令牌速率为2，预热期为3秒。由于设置了预热时间是3秒，
 * 令牌桶一开始并不会0.5秒发一个令牌，而是形成一个平滑线性下降的坡度，频率越来越高，在3秒钟之内达到原本设置的频率，
 * 以后就以固定的频率输出。这种功能适合系统刚启动需要一点时间来“热身”的场景。
 */
public class RateLimitTestTwo {
    public static void testSmoothwarmingUp() {
        RateLimiter r = RateLimiter.create(2, 3, TimeUnit.SECONDS);
        while (true) {
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("end");
        }
        /**
         * get 1 tokens: 0.0s
         * get 1 tokens: 1.332073s
         * get 1 tokens: 0.996782s
         * get 1 tokens: 0.666136s
         * end上边三次获取的时间相加正好为3秒
         * 下面的都是正常速度了， 正常速率0.5秒一个令牌
         * get 1 tokens: 0.498947s
         * get 1 tokens: 0.500018s
         * get 1 tokens: 0.499358s
         * get 1 tokens: 0.499349s
         */
    }

    public static void main(String[] args) {
        testSmoothwarmingUp();
    }

}
