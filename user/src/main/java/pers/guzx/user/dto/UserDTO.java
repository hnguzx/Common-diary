package pers.guzx.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pers.guzx.common.validation.Group;
import pers.guzx.user.entity.SysPermission;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/7 18:22
 * @describe
 */
@Data
public class UserDTO implements Serializable {
    private Integer userId;

    private String username;

    private Date birthday;

    private String sex;

    private String password;

    private String phone;

    private String email;

    @ApiModelProperty(value = "用户的角色信息")
    private List<RoleDTO> roleDTOList;

    @ApiModelProperty(value = "用户的权限信息")
    private List<SysPermission> permissionList;
}
