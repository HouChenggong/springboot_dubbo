package cn.net.health.user.dto;


import cn.net.health.user.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class UserRoleInfo extends SysUser implements Serializable {

    /**
     * 角色名称
     */
    private String roleName;


    /**
     * 角色标识
     */
    private String roleCode;


}
