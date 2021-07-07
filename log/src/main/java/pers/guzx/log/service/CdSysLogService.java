package pers.guzx.log.service;

import pers.guzx.common.service.BaseService;
import pers.guzx.log.entity.CdSysLog;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 10:13
 * @describe
 */
public interface CdSysLogService extends BaseService<CdSysLog> {
    public void saveLog(CdSysLog log);
}
