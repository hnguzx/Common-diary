package pers.guzx.log.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.guzx.common.mapper.BaseMapper;
import pers.guzx.common.serviceImpl.BaseServiceImpl;
import pers.guzx.log.entity.SysLog;
import pers.guzx.log.mapper.SysLogMapper;
import pers.guzx.log.service.SysLogService;

import javax.annotation.Resource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 10:15
 * @describe
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper logMapper;

    @Override
    public SysLog findById(int id) {
        return logMapper.selectByPrimaryKey(id);
    }

    @Override
    public int save(SysLog sysLog) {
        return logMapper.insert(sysLog);
    }

    @Override
    public int update(SysLog sysLog) {
        return logMapper.updateByPrimaryKeySelective(sysLog);
    }

    @Override
    public int remove(int id) {
        return logMapper.deleteByPrimaryKey(id);
    }
}
