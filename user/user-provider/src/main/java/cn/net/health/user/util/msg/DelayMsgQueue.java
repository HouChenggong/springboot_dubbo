package cn.net.health.user.util.msg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class DelayMsgQueue {

    private  Jedis  jedis;
    private String queue;

    /**
     * 消息入队
     *
     * @param data 要发送的消息
     */
    public void queue(Object data) {
        //构造一个 JavaboyMessage
        JavaboyMessage msg = new JavaboyMessage();
        msg.setId(UUID.randomUUID().toString());
        msg.setData(data);
        //序列化
        try {
            String s = new ObjectMapper().writeValueAsString(msg);
            System.out.println("msg publish:" + new Date());
            //消息发送，score 延迟 5 秒
            jedis.zadd(queue, System.currentTimeMillis() + 5000, s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息消费
     */
    public void loop() {
        while (!Thread.interrupted()) {
            //读取 score 在 0 到当前时间戳之间的消息
            Set<String> zrange = jedis.zrangeByScore(queue, 0, System.currentTimeMillis(), 0, 1);
            if (zrange.isEmpty()) {
                //如果消息是空的，则休息 500 毫秒然后继续
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            //如果读取到了消息，则直接读取消息出来
            String next = zrange.iterator().next();
            if (jedis.zrem(queue, next) > 0) {
                //抢到了，接下来处理业务
                try {
                    JavaboyMessage msg = new ObjectMapper().readValue(next, JavaboyMessage.class);
                    System.out.println("receive msg:" + msg);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
