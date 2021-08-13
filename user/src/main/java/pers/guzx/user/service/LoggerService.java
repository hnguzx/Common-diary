package pers.guzx.user.service;

import pers.guzx.common.entity.SysLog;

import javax.jms.JMSException;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/12 17:43
 * @describe
 */
public interface LoggerService {
    void log(SysLog sysLog) throws JMSException;
}
