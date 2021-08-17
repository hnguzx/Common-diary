package pers.guzx.uaa.entity;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "sys_user")
public class SysUserDetails implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer userId;
    private String username;
    private LocalDate birthday;

    private String sex;
    private String password;
    private String phone;
    private String email;
    private String role;

    @Column
    private Boolean accountNonExpired;
    @Column
    private Boolean accountNonLocked;
    @Column
    private Boolean credentialsNonExpired;
    @Column
    private Boolean enabled;

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
