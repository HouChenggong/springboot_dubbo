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
 * 系统用户表
 * </p>
 *
 * @author xiyou
 * @since 2019-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysUser对象", description="系统用户表")
public class SysUser extends Model<SysUser> {


    private static final long serialVersionUID = 8915100639778692399L;
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "user_id", type = IdType.UUID)
    private String userId;

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "昵称，默认是‘nick_name’")
    private String nickName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String passWord;

    @ApiModelProperty(value = "盐值")
    private String salt;

    @ApiModelProperty(value = "状态(0：正常，1：被锁定（多次尝试后账户被锁定)")
    private Boolean state;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String mail;

    @ApiModelProperty(value = "qq")
    private String qq;

    @ApiModelProperty(value = "未来支持头像url")
    private String imgUrl;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
