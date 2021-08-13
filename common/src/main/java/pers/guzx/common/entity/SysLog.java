package pers.guzx.common.entity;

import lombok.Data;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/12 17:44
 * @describe
 */
@Data
public class SysLog {
    private Long logId;
    private String username;
    private String userOperation;
    private String requestMethod;
    private String requestParams;
    private String requestIp;
}
