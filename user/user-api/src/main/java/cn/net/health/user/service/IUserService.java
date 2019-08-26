package cn.net.health.user.service;

import cn.net.health.user.dto.UserRoleInfo;
import cn.net.health.user.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */
public interface IUserService extends IService<SysUser> {

    /**
     * 根据用户名称查询用户
     *
     * @param userName
     * @return
     */
    SysUser findUserInfo(String userName);


    /**
     * 根据用户名称查询用户和角色
     *
     * @param userName
     * @return
     */
    UserRoleInfo findUserAndRoleInfo(String userName);




}
