package cn.net.health.user.impl;

import cn.net.health.user.entity.SysRole;
import cn.net.health.user.mapper.SysRoleMapper;
import cn.net.health.user.service.IRoleService;
import com.alibaba.dubbo.config.annotation.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */
@Slf4j
@Service(version = "1.0.0", timeout = 60000)
public class RoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements IRoleService {

    @Override
    public Boolean saveRole(SysRole role) {
        Boolean res = false;
        if (role.getRoleId() == null) {
            res = this.save(role);
        } else {
            res = this.updateById(role);
        }
        return res;
    }

    @Override
    public List<SysRole> listRoleAll() {
        return this.baseMapper.selectRoleAll();
    }
}
