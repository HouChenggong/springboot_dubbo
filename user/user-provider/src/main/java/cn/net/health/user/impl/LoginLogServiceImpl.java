package cn.net.health.user.impl;

import cn.net.health.user.entity.SysLoginLog;
import cn.net.health.user.mapper.SysLoginLogMapper;
import cn.net.health.user.service.ILoginLogService;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 系统登录日志表 服务实现类
 * </p>
 *
 * @author Auto Generator
 * @since 2018-10-01
 */
@Slf4j
@Service(version = "1.0.0", timeout = 60000)
public class LoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements ILoginLogService {

}
