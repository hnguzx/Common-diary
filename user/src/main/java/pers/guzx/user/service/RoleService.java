package pers.guzx.user.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pers.guzx.common.service.BaseService;
import pers.guzx.user.entity.SysRole;
import pers.guzx.user.entity.UserRole;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/19 11:35
 * @describe
 */
public interface RoleService extends BaseService<SysRole> {
    UserRole getUserRole(Integer userId);

    GrantedAuthority getRoleById(UserRole userRoles);

    UserRole getUserRoleByUser(UserDetails user);

    int saveUserRole(UserDetails user);
}
