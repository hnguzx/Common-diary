package pers.guzx.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pers.guzx.common.mapper.BaseMapper;
import pers.guzx.user.entity.SysUser;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 15:03
 * @describe
 */
@Mapper
public interface UserMapper extends BaseMapper<SysUser> {
}
