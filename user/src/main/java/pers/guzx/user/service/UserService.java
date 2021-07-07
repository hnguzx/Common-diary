package pers.guzx.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pers.guzx.common.service.BaseService;
import pers.guzx.user.entity.User;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 15:04
 * @describe
 */
public interface UserService extends BaseService<User>, UserDetailsService {
}
