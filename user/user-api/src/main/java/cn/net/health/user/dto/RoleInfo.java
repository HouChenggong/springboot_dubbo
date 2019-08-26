package cn.net.health.user.dto;

import cn.net.health.user.entity.SysPermission;
import cn.net.health.user.entity.SysRole;

import java.io.Serializable;
import java.util.List;

public class RoleInfo extends SysRole implements Serializable {

    private List<SysPermission> permissions;

    public List<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermission> permissions) {
        this.permissions = permissions;
    }
}
