package pers.guzx.notice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pers.guzx.common.mapper.BaseMapper;
import pers.guzx.notice.entity.SysMessage;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/13 10:02
 * @describe
 */
@Mapper
public interface MsgMapper extends BaseMapper<SysMessage> {
}
