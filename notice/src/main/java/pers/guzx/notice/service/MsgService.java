package pers.guzx.notice.service;

import pers.guzx.common.service.BaseService;
import pers.guzx.notice.entity.SysMessage;

import javax.mail.MessagingException;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/13 9:56
 * @describe
 */
public interface MsgService extends BaseService<SysMessage> {

    public Integer sendMail(SysMessage message) throws MessagingException;
}
