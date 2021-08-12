package pers.guzx.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.guzx.common.code.ErrorCode;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/7 10:00
 * @describe
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private ErrorCode errorCode;

    public BaseException() {
    }

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
