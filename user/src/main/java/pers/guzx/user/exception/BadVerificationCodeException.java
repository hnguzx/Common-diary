package pers.guzx.user.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021-07-27 下午 04:58
 * @describe
 */
public class BadVerificationCodeException extends AuthenticationException {
    public BadVerificationCodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BadVerificationCodeException(String msg) {
        super(msg);
    }
}
