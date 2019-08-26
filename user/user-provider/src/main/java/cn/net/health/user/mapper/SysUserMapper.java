package cn.net.health.user.mapper;


import cn.net.health.user.dto.UserRoleInfo;
import cn.net.health.user.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */

public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    SysUser findUserInfo(@Param("userName") String userName);

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    UserRoleInfo findRoleAndUser(@Param("userName") String userName);




}
