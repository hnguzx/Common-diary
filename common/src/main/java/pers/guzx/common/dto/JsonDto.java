package pers.guzx.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/2 17:03
 * @describe
 */
@Data
public class JsonDto<T> implements Serializable {

    /**
     * 正常响应码
     */
    private static final int SUCCESS_CODE = 200;
    /**
     * 正常响应信息
     */
    private static final String SUCCESS_MSG = "SUCCESS";

    private int code = SUCCESS_CODE;
    private String message = SUCCESS_MSG;
    private T data = null;
}
