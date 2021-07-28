package pers.guzx.user.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.guzx.user.entity.SysUserDetails;
import pers.guzx.user.entity.UserAuthority;
import pers.guzx.user.entity.UserRole;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/9 11:13
 * @describe
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private RoleServiceImpl roleService;

    @Resource
    private AuthorityServiceImpl authorityService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDetails user = (SysUserDetails) userService.getUserByUsername(username);
        UserRole userRoleByUser = roleService.getUserRoleByUser(user);
        GrantedAuthority role = roleService.getRoleById(userRoleByUser);

        List<UserAuthority> userAuthorityByUser = authorityService.getUserAuthorityByUser(user);
        List<GrantedAuthority> authority = authorityService.getAuthorityById(userAuthorityByUser);
        authority.add(role);
        user.setAuthorities(authority);
        return user;
    }
}
