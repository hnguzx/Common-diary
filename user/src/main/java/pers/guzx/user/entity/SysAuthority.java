package pers.guzx.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    @ApiModelProperty(value = "主键")
    private Integer authorityId;

    @ApiModelProperty(value = "权限名称")
    private String authorityName;

    @ApiModelProperty(value = "权限编码")
    private String authorityCode;

    @ApiModelProperty(value = "请求url")
    private String url;

    @Override
    public String getAuthority() {
        return this.url;
    }
}
