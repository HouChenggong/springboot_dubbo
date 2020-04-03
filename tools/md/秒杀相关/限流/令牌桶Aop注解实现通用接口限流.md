# 多值令牌桶+注解+AOP实现通用接口限流

目的：不同的接口我们限流的速率是不一样的，比如A接口只需要每秒100个限制，B接口每秒1000个

### 1. 自定义一个注解

```java
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
```

### 2. aop实现

```java

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/9 16:15
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {
    /**
     * 使用url做为key,存放令牌桶 防止每次重新创建令牌桶
     */
    private Map<String, RateLimiter> limitMap = Maps.newConcurrentMap();

    @Pointcut("@annotation(cn.monitor4all.miaoshaweb.limit.AnRateLimiter)")
    public void anRateLimiter() {
    }

    @Around("anRateLimiter()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取request,response
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        // 或者url(存在map集合的key)
        String url = request.getRequestURI();
        // 获取自定义注解
        AnRateLimiter rateLimiter = getAnRateLimiter(joinPoint);
        if (rateLimiter != null) {
            RateLimiter limiter = null;
            // 判断map集合中是否有创建有创建好的令牌桶
            if (!limitMap.containsKey(url)) {
                // 创建令牌桶
                limiter = RateLimiter.create(rateLimiter.permitsPerSecond());
                limitMap.put(url, limiter);
                log.info("<<=================  请求{},创建令牌桶,容量{} 成功!!!", url, rateLimiter.permitsPerSecond());
            }
            limiter = limitMap.get(url);
            // 获取令牌
            boolean acquire = limiter.tryAcquire(rateLimiter.timeout(), rateLimiter.timeunit());

            if (!acquire) {
                log.error("未获取到令牌");
                responseResult(response, 500, rateLimiter.msg());
                return null;
            }
            log.info("成功获取令牌：success");
        }
        return joinPoint.proceed();
    }

    /**
     * 获取注解对象
     *
     * @param joinPoint 对象
     * @return ten LogAnnotation
     */
    private AnRateLimiter getAnRateLimiter(final JoinPoint joinPoint) {
        Method[] methods = joinPoint.getTarget().getClass().getDeclaredMethods();
        String name = joinPoint.getSignature().getName();
        if (!StringUtils.isEmpty(name)) {
            for (Method method : methods) {
                AnRateLimiter annotation = method.getAnnotation(AnRateLimiter.class);
                if (!Objects.isNull(annotation) && name.equals(method.getName())) {
                    return annotation;
                }
            }
        }
        return null;
    }

    /**
     * 自定义响应结果
     *
     * @param response 响应
     * @param code     响应码
     * @param message  响应信息
     */
    private void responseResult(HttpServletResponse response, Integer code, String message) {
        response.resetBuffer();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.println("{\"code\":" + code + " ,\"message\" :\"" + message + "\"}");
            response.flushBuffer();
        } catch (IOException e) {
            log.error(" 输入响应出错 e = {}", e.getMessage(), e);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}

```

### 3. controller使用

```java
    @ApiOperation(value = "rateLimiter限流每秒10个", notes = "查询用户")
    @GetMapping("/index")
    @AnRateLimiter(permitsPerSecond = 10, timeout = 500, timeunit = TimeUnit.MILLISECONDS, msg = "亲,现在流量过大,请稍后再试.")
    public String index() {
        return System.currentTimeMillis() + "";
    }
    
    
        @ApiOperation(value = "rateLimiter限流每秒100个", notes = "查询用户")
    @GetMapping("/index2")
    @AnRateLimiter(permitsPerSecond = 100, timeout = 500, timeunit = TimeUnit.MILLISECONDS, msg = "亲,现在流量过大,请稍后再试.")
    public String index2() {
        return System.currentTimeMillis() + "";
    }
```

## github 关于秒杀的项目

[会一直迭代更新的秒杀项目](https://github.com/HouChenggong/miaosha)
[原创地址](https://github.com/qqxx6661/miaosha)

```java
https://github.com/HouChenggong/miaosha
当然这个项目是由一个大佬搭建的，我只是在其中添加了自己的一些东西，支持别人的原创
下面是他的地址：
https://github.com/qqxx6661/miaosha
```

