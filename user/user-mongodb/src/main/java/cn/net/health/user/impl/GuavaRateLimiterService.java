package cn.net.health.user.impl;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/9 14:04
 */
@Service
public class GuavaRateLimiterService {
    /*每秒控制5个许可*/
    RateLimiter rateLimiter = RateLimiter.create(5);


    /**
     * 获取令牌
     *
     * @return
     */
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }

}
