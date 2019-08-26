package cn.net.health.user.service;


import cn.net.health.user.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */
public interface IRoleService extends IService<SysRole> {

    Boolean saveRole(SysRole role);


    List<SysRole> listRoleAll();

}
