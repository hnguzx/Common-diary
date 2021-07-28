package pers.guzx.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 11:11
 * @describe
 */
@Data
@ApiModel(value = "Role对象", description = "角色表")
public class SysRole implements Serializable, GrantedAuthority {
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键")
    private Integer roleId;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "描述")
    private String roleDesc;

    @ApiModelProperty(value = "角色的权限")
    private List<SysAuthority> permissionList;

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
