package pers.guzx.uaa.service;

import org.springframework.security.core.userdetails.UserDetails;
import pers.guzx.common.service.BaseService;
import pers.guzx.uaa.entity.SysUserDetails;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/19 10:22
 * @describe
 */
public interface UserService extends BaseService<SysUserDetails> {
    UserDetails getUserByUsername(String username);
}
