package pers.guzx.user.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pers.guzx.common.validation.Group;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.GroupSequence;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Date;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 14:52
 * @describe
 */
@ApiModel
@Data
@Table(name = "user")
@Alias(value = "user")
@GroupSequence({Group.Add.class, Group.Update.class, User.class})
public class User implements UserDetails {

//    @Null(message = "新增时不用指定id", groups = {Group.Add.class})
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
    @Column(name = "birthday")
    private Date birthday;

    @NotBlank(message = "sex不能为空", groups = {Group.Add.class, Group.Update.class})
    @Column(name = "sex")
    private String sex;

    @NotBlank(message = "password不能为空", groups = {Group.Add.class, Group.Update.class})
    @Column(name = "password")
    private String password;

    @Length(message = "phone长度不正确",min = 10, max = 20, groups = {Group.Add.class, Group.Update.class})
    @Column(name = "phone")
    private String phone;

    @Email(message = "email格式不正确",groups = {Group.Add.class, Group.Update.class})
    @Column(name = "email")
    private String email;

//    @Null(message = "createTime",groups = {Group.Add.class, Group.Update.class})
    @Column(name = "create_time")
    private Date createTime;

//    @Null(message = "updateTime", groups = {Group.Add.class, Group.Update.class})
    @Column(name = "update_time")
    private Date updateTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
