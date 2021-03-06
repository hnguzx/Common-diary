package pers.guzx.log.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 10:04
 * @describe
 */
@Data
public class SysLog {
    
    @Id
    @Column(name = "log_id")
    private Long logId;
    private String username;
    private String userOperation;
    private String requestMethod;
    private String requestParams;
    private String requestIp;
}
