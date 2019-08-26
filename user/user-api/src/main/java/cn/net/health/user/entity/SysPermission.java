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
import java.util.Date;

/**
 * <p>
 * 系统权限表
 * </p>
 *
 * @author xiyou
 * @since 2019-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysPermission对象", description="系统权限表")
public class SysPermission extends Model<SysPermission> {


    private static final long serialVersionUID = -6478816509853008935L;
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "permission_id", type = IdType.UUID)
    private String permissionId;

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    @ApiModelProperty(value = "父权限ID，总的权限ID为自己")
    private String parentId;

    @ApiModelProperty(value = "父权限ID列表,不能不要大于5个,中间用三个,,,分割，如果是总权限，则为自己")
    private String parentIds;

    @ApiModelProperty(value = "权限编码")
    private String permissionCode;

    @ApiModelProperty(value = "资源类型(top_directory/directory/menu/button)")
    private String resourceType;

    @ApiModelProperty(value = "资源路径")
    private String url;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.permissionId;
    }

}
