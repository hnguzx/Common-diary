package pers.guzx.uaa.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 11:11
 * @describe
 */
@Data
public class SysRole implements Serializable, GrantedAuthority {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer roleId;

    private String roleCode;
    private String roleName;
    private String roleDesc;
    private List<SysAuthority> permissionList;

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
