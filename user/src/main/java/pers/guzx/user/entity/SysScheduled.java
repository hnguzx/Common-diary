package pers.guzx.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/6 15:04
 * @describe
 */
@Data
@ApiModel(value = "定时任务对象", description = "定时任务表")
public class SysScheduled {
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键")
    private Integer cronId;
    @ApiModelProperty(value = "定时任务完整类名")
    private String cronKey;
    @ApiModelProperty(value = "corn表达式")
    private String cronExpression;
    @ApiModelProperty(value = "定时任务描述")
    private String taskExplain;
    @ApiModelProperty(value = "状态")
    private Integer status;
}
