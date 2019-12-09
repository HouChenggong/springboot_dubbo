package cn.net.health.user.controller.limiter;


import cn.net.health.user.config.AnRateLimiter;
import cn.net.health.user.impl.GuavaRateLimiterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/11/6 11:45
 */
@RestController
@Api("test接口")
@RequestMapping("/limit/*")
@Slf4j
public class LimiterController {

    @Autowired
    private GuavaRateLimiterService guavaRateLimiterService;

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")
    })

    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public String deleteUser(@RequestParam("userId") String userId) {
        System.out.println("deleteUser:::" + userId);
        return "OK";
    }

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")})
    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    public String queryUser(@RequestParam("userId") String userId) {
        if (guavaRateLimiterService.tryAcquire()) {
            log.info("success");
            return "OK";
        }
        log.error("未获取到令牌！");
        return "false";


    }

    @ApiOperation(value = "rateLimiter限流", notes = "查询用户")
    @GetMapping("/index")
    @AnRateLimiter(permitsPerSecond = 10, timeout = 500, timeunit = TimeUnit.MILLISECONDS, msg = "亲,现在流量过大,请稍后再试.")
    public String index() {
        return System.currentTimeMillis() + "";
    }
}