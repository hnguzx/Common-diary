package pers.guzx.common.annotation;

import java.lang.annotation.*;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 10:34
 * @describe
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogger {
    String value() default "";
}
