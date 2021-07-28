package pers.guzx.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pers.guzx.common.validation.Group;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 14:52
 * @describe
 */
@Setter
@ApiModel(value = "User对象", description = "用户表")
@Table(name = "sys_user")
@GroupSequence({Group.Add.class, Group.Update.class, SysUserDetails.class})
public class SysUserDetails implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {Group.Update.class})
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer userId;

    @NotBlank(message = "username不能为空", groups = {Group.Add.class, Group.Update.class})
    private String username;

    @Past(message = "birthday不能大于当前日期", groups = {Group.Add.class, Group.Update.class})
    @NotNull(message = "birthday不能为空", groups = {Group.Add.class, Group.Update.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotBlank(message = "sex不能为空", groups = {Group.Add.class, Group.Update.class})
    private String sex;

    @NotBlank(message = "password不能为空", groups = {Group.Add.class, Group.Update.class})
    private String password;

    @Length(message = "phone长度不正确", min = 10, max = 20, groups = {Group.Add.class, Group.Update.class})
    private String phone;

    @Email(message = "email格式不正确", groups = {Group.Add.class, Group.Update.class})
    private String email;

    @NotBlank(message = "role不能为空", groups = {Group.Add.class})
    private String role;

    @Column
    private Boolean accountNonExpired;
    @Column
    private Boolean accountNonLocked;
    @Column
    private Boolean credentialsNonExpired;
    @Column
    private Boolean enabled;

    @ApiModelProperty(value = "用户的授权信息")
    private Collection<? extends GrantedAuthority> authorities;

    public Integer getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

//    public LocalDateTime getCreateTime() {
//        return createTime;
//    }
//
//    public LocalDateTime getUpdateTime() {
//        return updateTime;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
