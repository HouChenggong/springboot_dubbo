package cn.net.health.user.web.user;


import cn.net.health.user.dto.ResultInfo;
import cn.net.health.user.entity.SysUser;
import cn.net.health.user.service.IUserService;
import cn.net.health.user.shiro.ShiroKit;
import cn.net.health.user.util.JWTUtil;
import com.alibaba.dubbo.config.annotation.Reference;

import com.mydubbo.common.service.CacheService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api("用户登录和权限测试接口")
public class WebController {

    @Reference(version = "1.0.0")
    private IUserService userService;

    @Reference(version = "${demo.service.version}")
    private CacheService cacheService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @ApiOperation(value = "登录接口", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String")
    })
    @PostMapping("/login")
    public ResultInfo login(@RequestParam("username") String username,
                            @RequestParam("password") String password) {
        SysUser userBean = userService.findUserInfo(username);
        String encodePassword = ShiroKit.md5(password, password);
        if (userBean.getPassWord().equals(encodePassword)) {
            String jwt =JWTUtil.sign(username, encodePassword);
            redisTemplate.opsForValue().set("jwaat_"+username,jwt,12000L);
            stringRedisTemplate.opsForValue().set("jwt_"+username,jwt,12000L);
            cacheService.setCacheToRedis("1234", "撒旦阿松大2232", 6000L);
            String re = String.valueOf(cacheService.getCacheByKey("1234"));
            System.out.println(re+cacheService.getExpire("1234"));
            return new ResultInfo("200", "Login success",jwt );
        } else {
            throw new UnauthorizedException();
        }
    }

    @ApiOperation(value = "测试需要用户登录的接口", notes = "登录")
    @GetMapping("/subject")
    public ResultInfo article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResultInfo("200", "You are already logged in", null);
        } else {
            return new ResultInfo("200", "You are guest", null);
        }
    }

    @ApiOperation(value = "测试需要用户认证的接口2", notes = "登录")
    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResultInfo requireAuth() {
        return new ResultInfo("200", "You are authenticated", null);
    }

    @ApiOperation(value = "测试需要希尤角色的接口", notes = "登录")
    @GetMapping("/require_role_xiyou")
    @RequiresRoles("xiyou")
    public ResultInfo requireRole() {
        return new ResultInfo("200", "You are visiting require_role", null);
    }

    @ApiOperation(value = "测试需要权限的接口", notes = "登录")
    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResultInfo requirePermission() {
        return new ResultInfo("200", "You are visiting permission require edit,view", null);
    }

    @ApiOperation(value = "测试需要system权限的接口", notes = "登录")
    @GetMapping("/require_permission_system")
    @RequiresPermissions("system")
    public ResultInfo requirePermissionS() {
        return new ResultInfo("200", "You are visiting require_role", null);
    }

    @ApiOperation(value = "401没有权限的接口", notes = "登录")
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultInfo unauthorized() {
        return new ResultInfo("401", "Unauthorized", null);
    }
}
