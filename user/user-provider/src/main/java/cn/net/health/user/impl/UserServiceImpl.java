package cn.net.health.user.impl;

import cn.net.health.user.dto.UserRoleInfo;
import cn.net.health.user.entity.SysUser;
import cn.net.health.user.mapper.SysUserMapper;
import cn.net.health.user.service.IUserService;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */
@Slf4j
@Service(version = "1.0.0", timeout = 60000)
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements IUserService {

    @Override
    public SysUser findUserInfo(String userName) {
        return this.baseMapper.findUserInfo(userName);
    }

    @Override
    public UserRoleInfo findUserAndRoleInfo(String userName) {
        return this.baseMapper.findRoleAndUser(userName);
    }


}
