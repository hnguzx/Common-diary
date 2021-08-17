package pers.guzx.uaa.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 11:12
 * @describe
 */
@Data
public class SysAuthority implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer authorityId;
    private String authorityName;
    private String authorityCode;
    private String url;

    @Override
    public String getAuthority() {
        return this.url;
    }
}
