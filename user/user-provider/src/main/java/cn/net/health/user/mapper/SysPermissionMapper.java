package cn.net.health.user.mapper;


import cn.net.health.user.dto.PermissionInfo;
import cn.net.health.user.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统权限表 Mapper 接口
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */

public interface SysPermissionMapper extends BaseMapper<SysPermission> {


    /**
     * 根据角色ID查询权限
     * @param roleId
     * @return
     */
    Set<String> allPermissionByRoleId(@Param("roleId") String roleId);

}
