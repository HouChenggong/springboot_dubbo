package cn.net.health.user.test;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/9 14:15
 * 平滑突发限流
 *  使用RateLimiter的静态方法创建一个限流器，设置每秒放置的令牌数为5个。
 * 返回的RateLimiter对象可以保证1秒内不会给超过5个令牌，并且以固定速率进行放置，达到平滑输出的效果。
 */
public class RateLimterTest {
    public static void main(String[] args) {
        int[] messageCountList = new int[]{10000, 1000, 100};

        // 1000 tps
        RateLimiter rateLimiter = RateLimiter.create(1000);
        for (int messageCount : messageCountList) {
            rateLimiter.acquire(messageCount);
            try {
                slowRun(messageCount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 例1：传输速度比 RateLimiter 设定 tps 快，通过 RateLimiter 降低传输 tps。
     *
     * @param messageCount
     */
    private static void fastRun(int messageCount) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        System.out.println("传输 start time : " + formatter.format(new Date()));
        System.out.println("传输 message count : " + messageCount);
        System.out.println("传输 end time : " + formatter.format(new Date()));
        /**
         * 传输 start time : 09-十二月-2019 14:17:01:246
         * 传输 message count : 10000
         * 传输 end time : 09-十二月-2019 14:17:01:247
         * 传输 start time : 09-十二月-2019 14:17:11:212
         * 传输 message count : 1000
         * 传输 end time : 09-十二月-2019 14:17:11:212
         * 传输 start time : 09-十二月-2019 14:17:12:211
         * 传输 message count : 100
         * 传输 end time : 09-十二月-2019 14:17:12:211
         */
        /**
         *
         可见：
         1. 第一次尽管需要 10000 个 permits，但仍然是立即就拿到了。
         2. 第二次只需要 1000 个 permits，但需要补足第一次欠的 10000 个 permits，所以等了 10s。
         3. 第三次补足第二次欠的 1000 个 permits，所以等了 1s。
         4. 前后共耗时：11s。显然尽管消息传输很快，但总传输速度被降下来了。
         5. 从上可知：获取 permits 时，是先获取，再补足的方式。
          RateLimiter在没有足够令牌发放时，采用滞后处理的方式，也就是前一个请求获取令牌所需等待的时间由下一次请求来承受，也就是代替前一个请求进行等待。
         总结：简单的说就是，如果每次请求都为本次买单会有不必要的等待。
         比如说令牌增加的速度为每秒1个，初始时桶中没有令牌，这时来了个请求需要100个令牌，
         那需要等待100s后才能开始这个任务。所以更好的办法是先放行这个请求，然后延迟之后的请求。
         */
    }

    /**
     * 例2：传输速度比 RateLimiter 设定的 tps 慢。
     *
     * @param messageCount
     */
    private static void slowRun(int messageCount) throws InterruptedException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        System.out.println("方法启动时间" + formatter.format(new Date()));
        Thread.sleep(messageCount * 2);
        System.out.println("传输 start time : " + formatter.format(new Date()));
        System.out.println("传输 message count : " + messageCount);
        System.out.println("传输 end time : " + formatter.format(new Date()));

        /**
         * 方法启动时间09-十二月-2019 14:23:18:858
         * 传输 start time : 09-十二月-2019 14:23:38:859
         * 传输 message count : 10000
         * 传输 end time : 09-十二月-2019 14:23:38:859
         * 方法启动时间09-十二月-2019 14:23:38:859
         * 传输 start time : 09-十二月-2019 14:23:40:859
         * 传输 message count : 1000
         * 传输 end time : 09-十二月-2019 14:23:40:859
         * 方法启动时间09-十二月-2019 14:23:40:859
         * 传输 start time : 09-十二月-2019 14:23:41:061
         * 传输 message count : 100
         * 传输 end time : 09-十二月-2019 14:23:41:061
         */
        /**
         *
         可见：
         1. 第一次获取 10000 个 permits之前要休息10s*2就是20秒，20秒之后1万个令牌仍然是立即就拿到了。
         2. 第二次获取 1000 个 permits 时先休息2秒，由于第一次的 10000 条数据传输过慢，RateLimiter 已经累积了许多 permits，所以不需等待，直接获取了 1000 个 permits。
         3. 总共耗时 22s，由于 RateLimiter 的 tps 比传输速度快，所以 RateLimter 并不会额外限流。
         RateLimiter使用令牌桶算法，会进行令牌的累积，如果获取令牌的频率比较低，则不会导致等待，直接获取令牌。
         */
    }

}
