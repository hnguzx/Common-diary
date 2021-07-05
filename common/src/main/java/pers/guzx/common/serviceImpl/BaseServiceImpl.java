package pers.guzx.common.serviceImpl;

import pers.guzx.common.mapper.BaseMapper;
import pers.guzx.common.service.BaseService;

import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/5 13:58
 * @describe
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    public abstract BaseMapper<T> getMapper();

    @Override
    public T findById(int id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public int save(T t) {
        return getMapper().insert(t);
    }

    @Override
    public int update(T t) {
        return getMapper().updateByPrimaryKeySelective(t);
    }

    @Override
    public int remove(int id) {
        return getMapper().deleteByPrimaryKey(id);
    }
}
