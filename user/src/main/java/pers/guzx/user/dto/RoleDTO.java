package pers.guzx.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.guzx.user.entity.SysPermission;

import java.io.Serializable;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 14:18
 * @describe
 */
@Data
public class RoleDTO implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer roleId;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "描述")
    private String roleDesc;

    @ApiModelProperty(value = "角色的权限")
    private List<SysPermission> permissionList;

}
