package pers.guzx.user.service;

import pers.guzx.common.service.BaseService;
import pers.guzx.user.entity.SysScheduled;

import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/6 15:14
 * @describe
 */
public interface SysScheduledService extends BaseService<SysScheduled> {
    List<SysScheduled> selectAll();

    SysScheduled selectByScheduledKey(String key);
}
