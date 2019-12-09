package cn.net.health.user.config;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/9 16:14
 */


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AnRateLimiter {

    //以每秒固定数值往令牌桶添加令牌
    double permitsPerSecond();

    //获取令牌最大等待时间
    long timeout();

    // 单位(例:分钟/秒/毫秒) 默认:毫秒
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    // 无法获取令牌返回提示信息 默认值可以自行修改
    String msg() default "系统繁忙,请稍后再试.";
}