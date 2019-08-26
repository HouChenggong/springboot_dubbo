package cn.net.health.user.impl;


import cn.net.health.user.entity.SysLog;
import cn.net.health.user.mapper.LogMapper;
import cn.net.health.user.service.ILogService;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author Auto Generator
 * @since 2018-10-27
 */
@Slf4j
@Service(version = "1.0.0", timeout = 60000)
public class LogServiceImpl extends ServiceImpl<LogMapper, SysLog> implements ILogService {

}
