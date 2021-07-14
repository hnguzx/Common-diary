package pers.guzx.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 11:12
 * @describe
 */
@Data
public class SysUserRole implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer roleId;
}
