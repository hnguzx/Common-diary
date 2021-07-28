package pers.guzx.common.convert;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021-07-28 下午 06:17
 * @describe
 */
public interface DTOConvert<S, T> {
    public T convert(S s);
}
