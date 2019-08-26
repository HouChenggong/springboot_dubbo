package cn.net.health.user.mapper;


import cn.net.health.user.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */

public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectRoleAll();

}
