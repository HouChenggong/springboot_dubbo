package cn.net.health.user.global;

import cn.net.health.user.annotation.SysLogInterface;
import cn.net.health.user.dto.UserRoleInfo;
import cn.net.health.user.entity.SysLog;
import cn.net.health.user.service.ILogService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class WebLogAspect {



    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Reference(version = "1.0.0")
    private ILogService ilogService;

    @Pointcut("execution(public *  cn.net.health.user.web..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 请求参数
        Object[] args = joinPoint.getArgs();
        String requestParam = "";
        if (args != null && args.length > 0) {
            try {
                requestParam = JSONObject.toJSONString(args[0]);
            } catch (Exception e) {

            }
        }

        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : " + requestParam);

        // 添加系统操作日志
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        SysLogInterface sysLog = targetMethod.getAnnotation(SysLogInterface.class);
        if (sysLog != null) {
            UserRoleInfo userRoleInfo = (UserRoleInfo) SecurityUtils.getSubject().getPrincipal();
            SysLog log = new SysLog();
            log.setUserId(userRoleInfo.getUserId());
            log.setUserName(userRoleInfo.getUserName());
            log.setOperMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            log.setRequestParam(requestParam);
            log.setOperDesc(sysLog.value());
            ilogService.save(log);
        }

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
        log.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

}
