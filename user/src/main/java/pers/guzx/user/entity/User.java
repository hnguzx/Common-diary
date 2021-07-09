package pers.guzx.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pers.guzx.common.validation.Group;
import pers.guzx.user.dto.RoleDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.GroupSequence;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 14:52
 * @describe
 */
@ApiModel(value = "User对象", description = "用户表")
@Data
@Table(name = "user")
@Alias(value = "user")
@GroupSequence({Group.Add.class, Group.Update.class, User.class})
public class User implements UserDetails, Serializable {

    @NotNull(message = "id不能为空", groups = {Group.Update.class})
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "user_id")
    private Integer userId;

    @NotBlank(message = "username不能为空", groups = {Group.Add.class, Group.Update.class})
    @Column(name = "username")
    private String username;

    @Past(message = "birthday不能大于当前日期", groups = {Group.Add.class, Group.Update.class})
    @NotNull(message = "birthday不能为空", groups = {Group.Add.class, Group.Update.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthday;

    @NotBlank(message = "sex不能为空", groups = {Group.Add.class, Group.Update.class})
    @Column(name = "sex")
    private String sex;

    @NotBlank(message = "password不能为空", groups = {Group.Add.class, Group.Update.class})
    @Column(name = "password")
    private String password;

    @Length(message = "phone长度不正确", min = 10, max = 20, groups = {Group.Add.class, Group.Update.class})
    @Column(name = "phone")
    private String phone;

    @Email(message = "email格式不正确", groups = {Group.Add.class, Group.Update.class})
    @Column(name = "email")
    private String email;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "用户的角色信息")
    private List<SysRole> roleList;

    @ApiModelProperty(value = "用户的权限信息")
    private List<SysPermission> permissionList;

    @ApiModelProperty(value = "用户的授权信息")
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public User(){}

    public User(String username, String password, Collection authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
