package pers.guzx.log.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 10:04
 * @describe
 */
@ApiModel
@Alias(value = "log")
@Data
@Table(name = "cd_log")
public class SysLog {
    
    @Id
    @Column(name = "log_id")
    private Long id;
    @Column(name = "user_name")
    private String username;
    @Column(name = "user_operation")
    private String operation;
    @Column(name = "request_method")
    private String method;
    @Column(name = "request_params")
    private String params;
    @Column(name = "request_ip")
    private String ip;
    @Column(name = "create_time")
    private Date createTime;
}
