package com.mydubbo.common.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.mydubbo.common.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author xiyou
 * redis操作的接口
 */
@Slf4j
@Service(version = "${demo.service.version}")
public class CacheServiceImpl implements CacheService {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Object getCacheByKey(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public void setCacheToRedis(String key, Object value) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    @Override
    public void setCacheToRedis(String key, Object value, long time) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        if (time > 0) {
            valueOperations.set(key, value, time, TimeUnit.SECONDS);
        } else {
            valueOperations.set(key, value);
        }
    }

    @Override
    public <T> void setList(String key, List<T> os) {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        for (Object o : os) {
            listOperations.rightPush(key, o);
        }
    }

    @Override
    public <T> void setList(String key, List<T> os, long time) {
        if (time > 0) {
            ListOperations<String, Object> listOperations = redisTemplate.opsForList();
            for (Object o : os) {
                listOperations.rightPush(key, o);
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public <T> List<T> getList(String key) {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        List<T> o = null;
        if (listOperations.size(key) > 0) {
            o = (List<T>) listOperations.range(key, 0, -1);
        }
        return o;
    }

    @Override
    public boolean isExistKey(String key) {
        if (!StringUtils.isEmpty(key)) {
            return redisTemplate.hasKey(key);
        }
        return false;
    }

    @Override
    public void removeKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean isExistLock(String key) {
        boolean result = false;
        if (key != null && !"".equals(key)) {
            try {
                result = stringRedisTemplate.hasKey(key);
            } catch (Exception e) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean lock(String key, String value) {
        boolean result = false;
        try {
            boolean bool = stringRedisTemplate.opsForValue().setIfAbsent(key, value);
            if (bool) {
                //锁有效期60秒
                stringRedisTemplate.expire(key, 60, TimeUnit.SECONDS);
            }
            return bool;
        } catch (Exception e) {
            log.error("Redis加锁异常！");
        }
        return result;
    }

    @Override
    public void unLock(String key) {
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Redis解锁异常！");
        }
    }

    @Override
    public Set<String> getMatchPrefixKey(String prefix) {
        if (!StringUtils.isEmpty(prefix)) {
            Set<String> keys = stringRedisTemplate.keys(prefix + "*");
            if (keys == null || keys.size() == 0) {
                return null;
            }
            return keys;
        }
        return null;
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }
}
