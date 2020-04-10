# 为啥引入分布式锁

1. 比如我们秒杀下单
   - 第一步：判断订单是否存在，并且有库存
   - 第二步：秒杀订单，其实就是减少它的库存
   - 第三步：创建一个自己的订单
2. 但是这三步不是线程安全的，因为是先查，判断，然后更新，最后插入
3. 所以让它线程安全的保证其实就是保证这几个操作的原子性，用redis控制
4. redis控制原子性的核心是：setnx+expire就是同一时间只能有同一个操作进行



### 引入redis并配置基础配置

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis的通用配置
 *
 * @Author:debug (xiyouyan)
 * @Date: 2019/7/2 10:17
 **/
@Configuration
public class RedisConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //TODO:指定Key、Value的序列化策略
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }
}
```

