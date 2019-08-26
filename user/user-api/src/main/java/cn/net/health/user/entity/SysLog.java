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
 * 系统日志表
 * </p>
 *
 * @author xiyou
 * @since 2019-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysLog对象", description="系统日志表")
public class SysLog extends Model<SysLog> {


    private static final long serialVersionUID = -6965367105121409138L;
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "log_id", type = IdType.UUID)
    private String logId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "操作方法")
    private String operMethod;

    @ApiModelProperty(value = "操作参数")
    private String requestParam;

    @ApiModelProperty(value = "操作说明")
    private String operDesc;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.logId;
    }

}
