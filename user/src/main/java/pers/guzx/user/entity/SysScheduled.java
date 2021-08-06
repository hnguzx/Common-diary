package pers.guzx.user.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/6 15:04
 * @describe
 */
@Data
public class SysScheduled {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer cronId;
    private String cronKey;
    private String cronExpression;
    private String taskExplain;
    private Integer status;
}
