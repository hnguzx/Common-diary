package pers.guzx.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import pers.guzx.common.service.BaseService;
import pers.guzx.common.serviceImpl.BaseServiceImpl;
import pers.guzx.user.entity.SysUserDetails;

import javax.jms.JMSException;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/19 10:22
 * @describe
 */
public interface UserService extends BaseService<SysUserDetails> {
    UserDetails getUserByUsername(String username);

    void sendRegistryCode(String email) throws JMSException;

    void sendLoginCode(String email) throws JMSException;
}
