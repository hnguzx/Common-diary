package pers.guzx.common.service;

import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/5 10:52
 * @describe
 */
public interface BaseService<T> {

    T findById(int id);

    int save(T t);

    int update(T t);

    int remove(int id);
}
