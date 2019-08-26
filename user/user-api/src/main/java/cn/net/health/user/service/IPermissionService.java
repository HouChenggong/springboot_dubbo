package cn.net.health.user.service;


import cn.net.health.user.dto.MenuInfo;
import cn.net.health.user.dto.PermissionInfo;
import cn.net.health.user.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统权限表 服务类
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */
public interface IPermissionService extends IService<SysPermission> {

    List<SysPermission> getAllPermissions();

    Boolean savePermission(SysPermission permission);

    Boolean delBatchPermission(List<Integer> ids);


    /**
     * 根据用户ID查询所有权限
     *
     * @param roleId
     * @return
     */
    Set<String> allPermissionByRoleId(String roleId);

    List<MenuInfo> getMenuPermissions(String code);

    List<SysPermission> getTopDirectoryPermissions();

}
