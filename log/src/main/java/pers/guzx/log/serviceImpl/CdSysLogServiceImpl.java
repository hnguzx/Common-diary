package pers.guzx.log.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.guzx.common.mapper.BaseMapper;
import pers.guzx.common.serviceImpl.BaseServiceImpl;
import pers.guzx.log.entity.CdSysLog;
import pers.guzx.log.mapper.CdSysLogMapper;
import pers.guzx.log.service.CdSysLogService;

import javax.annotation.Resource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 10:15
 * @describe
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CdSysLogServiceImpl extends BaseServiceImpl<CdSysLog> implements CdSysLogService {

    @Resource
    private CdSysLogMapper logMapper;

    @Override
    public BaseMapper<CdSysLog> getMapper() {
        return this.logMapper;
    }

    @Override
    public void saveLog(CdSysLog log) {
        int insert = logMapper.insert(log);
    }
}
