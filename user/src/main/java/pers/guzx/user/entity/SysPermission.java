package pers.guzx.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 11:12
 * @describe
 */
@Data
public class SysPermission implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer permissionId;

    @ApiModelProperty(value = "父权限Id")
    private Integer parentId;

    @ApiModelProperty(value = "权限名称")
    private String permName;

    @ApiModelProperty(value = "权限编码")
    private String permCode;

    @ApiModelProperty(value = "请求url")
    private String url;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
