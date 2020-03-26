package cn.net.health.user.util.msg;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import redis.clients.jedis.Jedis;
public class DelayMsgTest {
    public static void main(String[] args) {
        CacheProperties.Redis redis = new CacheProperties.Redis();
        redis.execute(jedis -> {
            //构造一个消息队列
            DelayMsgQueue queue = new DelayMsgQueue(jedis, "javaboy-delay-queue");
            //构造消息生产者
            Thread producer = new Thread(){
                @Override
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        queue.queue("www.javaboy.org>>>>" + i);
                    }
                }
            };
            //构造一个消息消费者
            Thread consumer = new Thread(){
                @Override
                public void run() {
                    queue.loop();
                }
            };
            //启动
            producer.start();
            consumer.start();
            //休息 7 秒后，停止程序
            try {
                Thread.sleep(7000);
                consumer.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
