package cn.net.health.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xiyou
 * @since 2019-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysRolePermission对象", description="")
public class SysRolePermission extends Model<SysRolePermission> {


    private static final long serialVersionUID = 3254965703586945973L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "role_permission_id", type = IdType.UUID)
    private String rolePermissionId;

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "权限ID")
    private String permissionId;


    @Override
    protected Serializable pkVal() {
        return this.rolePermissionId;
    }

}
